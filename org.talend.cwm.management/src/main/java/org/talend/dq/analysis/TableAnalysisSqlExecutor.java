// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dq.analysis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.talend.cwm.db.connection.ConnectionUtils;
import org.talend.cwm.exception.AnalysisExecutionException;
import org.talend.cwm.helper.CatalogHelper;
import org.talend.cwm.helper.ColumnSetHelper;
import org.talend.cwm.helper.ResourceHelper;
import org.talend.cwm.helper.SchemaHelper;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.management.i18n.Messages;
import org.talend.cwm.relational.TdExpression;
import org.talend.cwm.relational.TdTable;
import org.talend.dataquality.PluginConstant;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.AnalysisResult;
import org.talend.dataquality.helpers.BooleanExpressionHelper;
import org.talend.dataquality.helpers.IndicatorHelper;
import org.talend.dataquality.indicators.CompositeIndicator;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.IndicatorParameters;
import org.talend.dataquality.indicators.definition.IndicatorDefinition;
import org.talend.dataquality.rules.JoinElement;
import org.talend.dataquality.rules.RulesPackage;
import org.talend.dataquality.rules.WhereRule;
import org.talend.dq.dbms.DbmsLanguage;
import org.talend.utils.collections.MultiMapHelper;
import org.talend.utils.sugars.ReturnCode;
import org.talend.utils.sugars.TypedReturnCode;
import orgomg.cwm.objectmodel.core.Expression;
import orgomg.cwm.objectmodel.core.ModelElement;
import orgomg.cwm.objectmodel.core.Package;
import orgomg.cwm.resource.relational.Catalog;
import orgomg.cwm.resource.relational.NamedColumnSet;
import orgomg.cwm.resource.relational.RelationalPackage;
import orgomg.cwm.resource.relational.Schema;

import Zql.ParseException;

/**
 * DOC xqliu class global comment. Detailled comment
 */
public class TableAnalysisSqlExecutor extends TableAnalysisExecutor {

    private static Logger log = Logger.getLogger(TableAnalysisSqlExecutor.class);

    private DbmsLanguage dbmsLanguage;

    // protected Analysis cachedAnalysis;

    @Override
    protected String createSqlStatement(Analysis analysis) {
        this.cachedAnalysis = analysis;
        AnalysisResult results = analysis.getResults();
        assert results != null;
        try {
            // --- get data filter
            TableAnalysisHandler handler = new TableAnalysisHandler();
            handler.setAnalysis(analysis);
            String stringDataFilter = handler.getStringDataFilter();
            // --- get all the leaf indicators used for the sql computation
            Collection<Indicator> leafIndicators = IndicatorHelper.getIndicatorLeaves(results);
            // --- create one sql statement for each leaf indicator
            for (Indicator indicator : leafIndicators) {
                if (!createSqlQuery(stringDataFilter, indicator)) {
                    log.error(Messages.getString("ColumnAnalysisSqlExecutor.CREATEQUERYERROR") + indicator.getName());//$NON-NLS-1$
                    return null;
                }
            }
        } catch (ParseException e) {
            log.error(e, e);
            return null;
        } catch (AnalysisExecutionException e) {
            log.error(e, e);
            return null;
        }
        return PluginConstant.EMPTY_STRING;
    }

    private boolean createSqlQuery(String dataFilterAsString, Indicator indicator) throws ParseException,
            AnalysisExecutionException {
        ModelElement analyzedElement = indicator.getAnalyzedElement();
        if (analyzedElement == null) {
            return traceError("Analyzed element is null for indicator " + indicator.getName());//$NON-NLS-1$
        }
        NamedColumnSet set = SwitchHelpers.NAMED_COLUMN_SET_SWITCH.doSwitch(indicator.getAnalyzedElement());
        if (set == null) {
            return traceError("Analyzed element is not a table for indicator " + indicator.getName());//$NON-NLS-1$
        }
        // --- get the schema owner
        String setName = quote(set.getName());
        if (!belongToSameSchemata(set)) {
            StringBuffer buf = new StringBuffer();
            for (orgomg.cwm.objectmodel.core.Package schema : schemata.values()) {
                buf.append(schema.getName() + " "); //$NON-NLS-1$
            }
            log.error(Messages
                    .getString("ColumnAnalysisSqlExecutor.COLUMNNOTBELONGTOEXISTSCHEMA", setName, buf.toString().trim()));//$NON-NLS-1$
            return false;
        }

        // get correct language for current database
        String language = dbms().getDbmsName();
        Expression sqlGenericExpression = null;

        // --- create select statement
        // get indicator's sql tableS (generate the real SQL statement from its definition)

        IndicatorDefinition indicatorDefinition = indicator.getIndicatorDefinition();
        if (indicatorDefinition == null) {
            return traceError(Messages.getString("ColumnAnalysisSqlExecutor.INTERNALERROR", indicator.getName()));//$NON-NLS-1$
        }
        sqlGenericExpression = dbms().getSqlExpression(indicatorDefinition);

        final EClass indicatorEclass = indicator.eClass();
        if (sqlGenericExpression == null || sqlGenericExpression.getBody() == null) {
            return traceError(Messages.getString(
                    "ColumnAnalysisSqlExecutor.UNSUPPORTEDINDICATOR",//$NON-NLS-1$
                    (indicator.getName() != null ? indicator.getName() : indicatorEclass.getName()),
                    ResourceHelper.getUUID(indicatorDefinition)));
        }

        // --- get indicator parameters and convert them into sql expression
        List<String> whereExpression = new ArrayList<String>();
        if (StringUtils.isNotBlank(dataFilterAsString)) {
            whereExpression.add(dataFilterAsString);
        }
        final EList<JoinElement> joinConditions = indicator.getJoinConditions();
        if (RulesPackage.eINSTANCE.getWhereRule().equals(indicatorDefinition.eClass())) {
            WhereRule wr = (WhereRule) indicatorDefinition;
            whereExpression.add(wr.getWhereExpression());

            // MOD scorreia 2009-03-13 copy joins conditions into the indicator
            joinConditions.clear();
            if (!wr.getJoins().isEmpty()) {
                for (JoinElement joinelt : wr.getJoins()) {
                    JoinElement joinCopy = (JoinElement) EcoreUtil.copy(joinelt);
                    joinConditions.add(joinCopy);
                }
            }
        }
        // FIXME parameters is never used, remove it.
        IndicatorParameters parameters = indicator.getParameters();
        if (parameters != null) {
            // TODO handle parameters here
        }

        String schemaName = getQuotedSchemaName(set);

        // --- normalize table name
        String catalogName = getQuotedCatalogName(set);
        if (catalogName == null && schemaName != null) {
            // try to get catalog above schema
            final Schema parentSchema = SchemaHelper.getParentSchema(set);
            final Catalog parentCatalog = CatalogHelper.getParentCatalog(parentSchema);
            catalogName = parentCatalog != null ? parentCatalog.getName() : null;
        }
        // MOD by zshen: change schemaName of sybase database to Table's owner.
        if (ConnectionUtils.isSybaseeDBProducts(dbms().getDbmsName())) {
            schemaName = ColumnSetHelper.getTableOwner(set);
        }
        // ~11934

        setName = dbms().toQualifiedName(catalogName, schemaName, setName);

        // ### evaluate SQL Statement depending on indicators ###
        String completedSqlString = null;

        // --- default case
        // allow join
        String joinclause = (!joinConditions.isEmpty()) ? dbms().createLeftJoinConditionAsString(set, joinConditions,
                catalogName, schemaName) : PluginConstant.EMPTY_STRING;

        completedSqlString = dbms().fillGenericQueryWithJoin(sqlGenericExpression.getBody(), setName, joinclause);
        // ~
        completedSqlString = addWhereToSqlStringStatement(whereExpression, completedSqlString, true);

        // completedSqlString is the final query
        String finalQuery = completedSqlString;

        TdExpression instantiateSqlExpression = BooleanExpressionHelper.createTdExpression(language, finalQuery);
        indicator.setInstantiatedExpression(instantiateSqlExpression);
        return true;
    }

    protected boolean traceError(String error) {
        this.errorMessage = error;
        log.error(this.errorMessage);
        return false;
    }

    protected boolean belongToSameSchemata(final TdTable tdTable) {
        assert tdTable != null;
        if (schemata.get(tdTable) != null) {
            return true;
        }
        // get catalog or schema
        Package schema = ColumnSetHelper.getParentCatalogOrSchema(tdTable);
        if (schema == null) {
            this.errorMessage = Messages.getString("TableAnalysisSqlExecutor.NoSchemaOrCatalogFound", tdTable.getName()); //$NON-NLS-1$
            return false;
        }
        schemata.put(tdTable, schema);
        return true;
    }

    protected DbmsLanguage dbms() {
        if (this.dbmsLanguage == null) {
            this.dbmsLanguage = createDbmsLanguage();
        }
        return this.dbmsLanguage;
    }

    /**
     * add the where clause to the sql statement.
     * 
     * @param whereExpressions the list of where expressions to concatenate (must not be null)
     * @param completedSqlString a generic SQL expression in which the where clause variable will be replaced.
     * @param valid if false add ! before where clause
     * @return the SQL statement with the where clause
     * @throws ParseException
     */
    private String addWhereToSqlStringStatement(List<String> whereExpressions, String completedSqlString, boolean valid)
            throws ParseException {
        return dbms().addWhereToSqlStringStatement(completedSqlString, whereExpressions, valid);
    }

    @Override
    protected boolean runAnalysis(Analysis analysis, String sqlStatement) {
        boolean ok = true;

        // reset the connection pool before run this analysis
        resetConnectionPool(analysis);

        // TypedReturnCode<Connection> trc = this.getConnection(analysis);
        // if (!trc.isOk()) {
        // return traceError(Messages.getString(
        //                    "FunctionalDependencyExecutor.CANNOTEXECUTEANALYSIS", analysis.getName(), trc.getMessage()));//$NON-NLS-1$
        // }

        TypedReturnCode<java.sql.Connection> trc = null;
        if (POOLED_CONNECTION) {
            trc = getPooledConnection(analysis);
        } else {
            trc = getConnection(analysis);
        }

        if (!trc.isOk()) {
            log.error(trc.getMessage());
            this.errorMessage = trc.getMessage();
            return traceError(Messages.getString(
                    "FunctionalDependencyExecutor.CANNOTEXECUTEANALYSIS", analysis.getName(), trc.getMessage()));//$NON-NLS-1$
        }

        Connection connection = trc.getObject();
        try {
            // store map of element to each indicator used for computation (leaf indicator)
            Map<ModelElement, List<Indicator>> elementToIndicator = new HashMap<ModelElement, List<Indicator>>();

            // execute the sql statement for each indicator
            Collection<Indicator> indicators = IndicatorHelper.getIndicatorLeaves(analysis.getResults());
            for (Indicator indicator : indicators) {
                // skip composite indicators that do not require a sql execution
                if (indicator instanceof CompositeIndicator) {
                    // options of composite indicators are handled elsewhere
                    continue;
                }
                // set the connection's catalog
                String catalogName = getCatalogOrSchemaName(indicator.getAnalyzedElement());
                if (catalogName != null) { // check whether null argument can be given
                    changeCatalog(catalogName, connection);
                }

                Expression query = dbms().getInstantiatedExpression(indicator);
                if (query == null || !executeQuery(indicator, connection, query.getBody())) {
                    ok = traceError("Query not executed for indicator: \"" + indicator.getName() + "\" "//$NON-NLS-1$//$NON-NLS-2$
                            + ((query == null) ? "query is null" : "SQL query: " + query.getBody()));//$NON-NLS-1$//$NON-NLS-2$
                } else {
                    indicator.setComputed(true);
                }

                // add mapping of analyzed elements to their indicators
                MultiMapHelper.addUniqueObjectToListMap(indicator.getAnalyzedElement(), indicator, elementToIndicator);

            }

            if (POOLED_CONNECTION) {
                releasePooledConnection(analysis, connection, true);
            } else {
                ReturnCode rc = ConnectionUtils.closeConnection(connection);
                ok = rc.isOk();
                if (!ok) {
                    this.errorMessage = rc.getMessage();
                }
            }

            // --- finalize indicators by setting the row count and null when they exist.
            ColumnAnalysisSqlExecutor finalization = new ColumnAnalysisSqlExecutor();
            finalization.setRowCountAndNullCount(elementToIndicator);
        } catch (Exception e) {
            log.error(e, e);
            this.errorMessage = e.getMessage();
            ok = false;
        }
        // finally {
        // ConnectionUtils.closeConnection(connection);
        // }
        return ok;
    }

    private String getCatalogOrSchemaName(ModelElement analyzedElement) {
        Package schema = super.schemata.get(analyzedElement);
        if (schema == null) {
            log.error(Messages.getString("TableAnalysisSqlExecutor.NOSCHEMAFOUNDFORTABLE", analyzedElement.getName()));//$NON-NLS-1$
            return null;
        }
        // else
        if (RelationalPackage.eINSTANCE.getSchema().equals(schema.eClass())) {
            final Catalog parentCatalog = CatalogHelper.getParentCatalog(schema);
            if (parentCatalog != null) {
                return parentCatalog.getName();
            }
        }
        return schema.getName();
    }

    protected boolean changeCatalog(String catalogName, Connection connection) {
        try {
            if (!(ConnectionUtils.isOdbcMssql(connection) || ConnectionUtils.isOdbcOracle(connection)
                    || ConnectionUtils.isOdbcProgress(connection) || ConnectionUtils.isOdbcTeradata(connection))) {
                connection.setCatalog(catalogName);
            }
            return true;
        } catch (RuntimeException e) {
            return traceError(Messages.getString("ColumnAnalysisSqlExecutor.ERRORWHENSETCATALOG", catalogName, e.getMessage()));//$NON-NLS-1$
        } catch (SQLException e) {
            return traceError(Messages.getString("ColumnAnalysisSqlExecutor.ERRORWHENSETCATALOGSQL", catalogName, e.getMessage()));//$NON-NLS-1$
        }
    }

    private boolean executeQuery(Indicator indicator, Connection connection, String queryStmt) throws SQLException {
        String cat = getCatalogOrSchemaName(indicator.getAnalyzedElement());
        if (log.isInfoEnabled()) {
            log.info(Messages.getString("ColumnAnalysisSqlExecutor.COMPUTINGINDICATOR", indicator.getName()));//$NON-NLS-1$
        }
        List<Object[]> myResultSet = executeQuery(cat, connection, queryStmt);

        // give result to indicator so that it handles the results
        return indicator.storeSqlResults(myResultSet);
    }

    protected List<Object[]> executeQuery(String catalogName, Connection connection, String queryStmt) throws SQLException {
        if (catalogName != null) { // check whether null argument can be given
            changeCatalog(catalogName, connection);
        }
        // create query statement
        Statement statement = connection.createStatement();
        // statement.setFetchSize(fetchSize);
        if (log.isInfoEnabled()) {
            log.info(Messages.getString("ColumnAnalysisSqlExecutor.EXECUTINGQUERY", queryStmt));//$NON-NLS-1$
        }
        if (continueRun()) {
            statement.execute(queryStmt);
        }

        // get the results
        ResultSet resultSet = statement.getResultSet();
        if (resultSet == null) {
            String mess = Messages.getString("ColumnAnalysisSqlExecutor.NORESULTSETFORTHISSTATEMENT") + queryStmt;//$NON-NLS-1$
            log.warn(mess);
            return null;
        }
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Object[]> myResultSet = new ArrayList<Object[]>();
        while (resultSet.next()) {
            Object[] result = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                result[i] = resultSet.getObject(i + 1);
            }
            myResultSet.add(result);
        }
        // -- release resources
        resultSet.close();
        statement.close();

        return myResultSet;
    }

    /**
     * DOC xqliu Comment method "getValidStatement". 2009-10-29 bug 9702
     * 
     * @param dataFilterAsString
     * @param indicator
     * @param valid
     * @return
     */
    public String getValidStatement(String dataFilterAsString, Indicator indicator, boolean valid) {
        ModelElement analyzedElement = indicator.getAnalyzedElement();
        if (analyzedElement == null) {
            traceError(Messages.getString("ColumnAnalysisSqlExecutor.ANALYSISELEMENTISNULL", indicator.getName()));//$NON-NLS-1$
            return PluginConstant.EMPTY_STRING;
        }
        NamedColumnSet set = SwitchHelpers.NAMED_COLUMN_SET_SWITCH.doSwitch(indicator.getAnalyzedElement());
        if (set == null) {
            traceError(Messages.getString("TableAnalysisSqlExecutor.ANALYZEDELEMENTISNOTATABLE", indicator.getName()));//$NON-NLS-1$
            return PluginConstant.EMPTY_STRING;
        }
        // --- get the schema owner
        String setName = quote(set.getName());
        if (!belongToSameSchemata(set)) {
            StringBuffer buf = new StringBuffer();
            for (orgomg.cwm.objectmodel.core.Package schema : schemata.values()) {
                buf.append(schema.getName() + " "); //$NON-NLS-1$
            }
            log.error(Messages.getString("TableAnalysisSqlExecutor.TABLENOTBELONGTOEXISSCHEMA", setName, buf.toString().trim()));//$NON-NLS-1$
            return PluginConstant.EMPTY_STRING;
        }

        // get correct language for current database
        Expression sqlGenericExpression = null;

        // --- create select statement
        // get indicator's sql tableS (generate the real SQL statement from its definition)

        IndicatorDefinition indicatorDefinition = indicator.getIndicatorDefinition();
        if (indicatorDefinition == null) {
            traceError(Messages.getString("ColumnAnalysisSqlExecutor.INTERNALERROR", indicator.getName()));//$NON-NLS-1$
            return PluginConstant.EMPTY_STRING;
        }
        sqlGenericExpression = dbms().getSqlExpression(indicatorDefinition);

        final EClass indicatorEclass = indicator.eClass();
        if (sqlGenericExpression == null || sqlGenericExpression.getBody() == null) {
            traceError(Messages
                    .getString(
                            "ColumnAnalysisSqlExecutor.UNSUPPORTEDINDICATOR", (indicator.getName() != null ? indicator.getName() : indicatorEclass.getName()), ResourceHelper.getUUID(indicatorDefinition)));//$NON-NLS-1$
            return PluginConstant.EMPTY_STRING;
        }

        // --- get indicator parameters and convert them into sql expression
        List<String> whereExpression = new ArrayList<String>();
        if (StringUtils.isNotBlank(dataFilterAsString)) {
            whereExpression.add(dataFilterAsString);
        }
        String setAliasA = PluginConstant.EMPTY_STRING;
        final EList<JoinElement> joinConditions = indicator.getJoinConditions();
        if (RulesPackage.eINSTANCE.getWhereRule().equals(indicatorDefinition.eClass())) {
            WhereRule wr = (WhereRule) indicatorDefinition;
            whereExpression.add(wr.getWhereExpression());

            // MOD scorreia 2009-03-13 copy joins conditions into the indicator
            joinConditions.clear();
            if (!wr.getJoins().isEmpty()) {
                for (JoinElement joinelt : wr.getJoins()) {
                    JoinElement joinCopy = (JoinElement) EcoreUtil.copy(joinelt);
                    joinConditions.add(joinCopy);
                    setAliasA = PluginConstant.EMPTY_STRING.equals(setAliasA) ? joinCopy.getTableAliasA() : setAliasA;
                }
            }
        }

        String schemaName = getQuotedSchemaName(set);

        // --- normalize table name
        String catalogName = getQuotedCatalogName(set);
        if (catalogName == null && schemaName != null) {
            // try to get catalog above schema
            final Schema parentSchema = SchemaHelper.getParentSchema(set);
            final Catalog parentCatalog = CatalogHelper.getParentCatalog(parentSchema);
            catalogName = parentCatalog != null ? parentCatalog.getName() : null;
        }
        // MOD by zshen: change schemaName of sybase database to Table's owner.
        if (ConnectionUtils.isSybaseeDBProducts(dbms().getDbmsName())) {
            schemaName = ColumnSetHelper.getTableOwner(set);
        }
        // ~11934
        setName = dbms().toQualifiedName(catalogName, schemaName, setName);

        // ### evaluate SQL Statement depending on indicators ###
        String completedSqlString = null;

        // --- default case
        // allow join
        String joinclause = (!joinConditions.isEmpty()) ? dbms().createLeftJoinConditionAsString(set, joinConditions,
                catalogName, schemaName) : PluginConstant.EMPTY_STRING;

        String genericSql = sqlGenericExpression.getBody();
        setAliasA = PluginConstant.EMPTY_STRING.equals(setAliasA) ? "*" : setAliasA + ".*";//$NON-NLS-1$//$NON-NLS-2$
        genericSql = genericSql.replace("COUNT(*)", setAliasA);//$NON-NLS-1$

        completedSqlString = dbms().fillGenericQueryWithJoin(genericSql, setName, joinclause);
        // ~
        try {
            completedSqlString = addWhereToSqlStringStatement(whereExpression, completedSqlString, valid);
        } catch (ParseException e) {
            log.warn(e);
        }

        return completedSqlString;
    }

    /**
     * DOC xqliu Comment method "setCachedAnalysis". 2009-10-29 bug 9702
     * 
     * @param analysis
     */
    public void setCachedAnalysis(Analysis analysis) {
        this.cachedAnalysis = analysis;
    }
}
