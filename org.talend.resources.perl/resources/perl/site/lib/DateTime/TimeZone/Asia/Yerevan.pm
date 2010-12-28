# This file is auto-generated by the Perl DateTime Suite time zone
# code generator (0.07) This code generator comes with the
# DateTime::TimeZone module distribution in the tools/ directory

#
# Generated from /tmp/GaPAi6WTRf/asia.  Olson data version 2009c
#
# Do not edit this file directly.
#
package DateTime::TimeZone::Asia::Yerevan;

use strict;

use Class::Singleton;
use DateTime::TimeZone;
use DateTime::TimeZone::OlsonDB;

@DateTime::TimeZone::Asia::Yerevan::ISA = ( 'Class::Singleton', 'DateTime::TimeZone' );

my $spans =
[
    [
DateTime::TimeZone::NEG_INFINITY,
60694520520,
DateTime::TimeZone::NEG_INFINITY,
60694531200,
10680,
0,
'LMT'
    ],
    [
60694520520,
61730542800,
60694531320,
61730553600,
10800,
0,
'YERT'
    ],
    [
61730542800,
62490600000,
61730557200,
62490614400,
14400,
0,
'YERT'
    ],
    [
62490600000,
62506407600,
62490618000,
62506425600,
18000,
1,
'YERST'
    ],
    [
62506407600,
62522136000,
62506422000,
62522150400,
14400,
0,
'YERT'
    ],
    [
62522136000,
62537943600,
62522154000,
62537961600,
18000,
1,
'YERST'
    ],
    [
62537943600,
62553672000,
62537958000,
62553686400,
14400,
0,
'YERT'
    ],
    [
62553672000,
62569479600,
62553690000,
62569497600,
18000,
1,
'YERST'
    ],
    [
62569479600,
62585294400,
62569494000,
62585308800,
14400,
0,
'YERT'
    ],
    [
62585294400,
62601026400,
62585312400,
62601044400,
18000,
1,
'YERST'
    ],
    [
62601026400,
62616751200,
62601040800,
62616765600,
14400,
0,
'YERT'
    ],
    [
62616751200,
62632476000,
62616769200,
62632494000,
18000,
1,
'YERST'
    ],
    [
62632476000,
62648200800,
62632490400,
62648215200,
14400,
0,
'YERT'
    ],
    [
62648200800,
62663925600,
62648218800,
62663943600,
18000,
1,
'YERST'
    ],
    [
62663925600,
62679650400,
62663940000,
62679664800,
14400,
0,
'YERT'
    ],
    [
62679650400,
62695375200,
62679668400,
62695393200,
18000,
1,
'YERST'
    ],
    [
62695375200,
62711100000,
62695389600,
62711114400,
14400,
0,
'YERT'
    ],
    [
62711100000,
62726824800,
62711118000,
62726842800,
18000,
1,
'YERST'
    ],
    [
62726824800,
62742549600,
62726839200,
62742564000,
14400,
0,
'YERT'
    ],
    [
62742549600,
62758274400,
62742567600,
62758292400,
18000,
1,
'YERST'
    ],
    [
62758274400,
62773999200,
62758288800,
62774013600,
14400,
0,
'YERT'
    ],
    [
62773999200,
62790328800,
62774017200,
62790346800,
18000,
1,
'YERST'
    ],
    [
62790328800,
62806053600,
62790343200,
62806068000,
14400,
0,
'YERT'
    ],
    [
62806053600,
62821252800,
62806068000,
62821267200,
14400,
1,
'YERST'
    ],
    [
62821252800,
62821782000,
62821267200,
62821796400,
14400,
1,
'AMST'
    ],
    [
62821782000,
62837496000,
62821792800,
62837506800,
10800,
0,
'AMT'
    ],
    [
62837496000,
62853217200,
62837510400,
62853231600,
14400,
1,
'AMST'
    ],
    [
62853217200,
62868956400,
62853228000,
62868967200,
10800,
0,
'AMT'
    ],
    [
62868956400,
62884681200,
62868970800,
62884695600,
14400,
1,
'AMST'
    ],
    [
62884681200,
62900406000,
62884692000,
62900416800,
10800,
0,
'AMT'
    ],
    [
62900406000,
62916130800,
62900420400,
62916145200,
14400,
1,
'AMST'
    ],
    [
62916130800,
62931855600,
62916141600,
62931866400,
10800,
0,
'AMT'
    ],
    [
62931855600,
62947580400,
62931870000,
62947594800,
14400,
1,
'AMST'
    ],
    [
62947580400,
62987745600,
62947594800,
62987760000,
14400,
0,
'AMT'
    ],
    [
62987745600,
62995356000,
62987760000,
62995370400,
14400,
0,
'AMT'
    ],
    [
62995356000,
63013500000,
62995374000,
63013518000,
18000,
1,
'AMST'
    ],
    [
63013500000,
63026805600,
63013514400,
63026820000,
14400,
0,
'AMT'
    ],
    [
63026805600,
63044949600,
63026823600,
63044967600,
18000,
1,
'AMST'
    ],
    [
63044949600,
63058255200,
63044964000,
63058269600,
14400,
0,
'AMT'
    ],
    [
63058255200,
63077004000,
63058273200,
63077022000,
18000,
1,
'AMST'
    ],
    [
63077004000,
63089704800,
63077018400,
63089719200,
14400,
0,
'AMT'
    ],
    [
63089704800,
63108453600,
63089722800,
63108471600,
18000,
1,
'AMST'
    ],
    [
63108453600,
63121154400,
63108468000,
63121168800,
14400,
0,
'AMT'
    ],
    [
63121154400,
63139903200,
63121172400,
63139921200,
18000,
1,
'AMST'
    ],
    [
63139903200,
63153208800,
63139917600,
63153223200,
14400,
0,
'AMT'
    ],
    [
63153208800,
63171352800,
63153226800,
63171370800,
18000,
1,
'AMST'
    ],
    [
63171352800,
63184658400,
63171367200,
63184672800,
14400,
0,
'AMT'
    ],
    [
63184658400,
63202802400,
63184676400,
63202820400,
18000,
1,
'AMST'
    ],
    [
63202802400,
63216108000,
63202816800,
63216122400,
14400,
0,
'AMT'
    ],
    [
63216108000,
63234856800,
63216126000,
63234874800,
18000,
1,
'AMST'
    ],
    [
63234856800,
63247557600,
63234871200,
63247572000,
14400,
0,
'AMT'
    ],
    [
63247557600,
63266306400,
63247575600,
63266324400,
18000,
1,
'AMST'
    ],
    [
63266306400,
63279007200,
63266320800,
63279021600,
14400,
0,
'AMT'
    ],
    [
63279007200,
63297756000,
63279025200,
63297774000,
18000,
1,
'AMST'
    ],
    [
63297756000,
63310456800,
63297770400,
63310471200,
14400,
0,
'AMT'
    ],
    [
63310456800,
63329205600,
63310474800,
63329223600,
18000,
1,
'AMST'
    ],
    [
63329205600,
63342511200,
63329220000,
63342525600,
14400,
0,
'AMT'
    ],
    [
63342511200,
63360655200,
63342529200,
63360673200,
18000,
1,
'AMST'
    ],
    [
63360655200,
63373960800,
63360669600,
63373975200,
14400,
0,
'AMT'
    ],
    [
63373960800,
63392104800,
63373978800,
63392122800,
18000,
1,
'AMST'
    ],
    [
63392104800,
63405410400,
63392119200,
63405424800,
14400,
0,
'AMT'
    ],
    [
63405410400,
63424159200,
63405428400,
63424177200,
18000,
1,
'AMST'
    ],
    [
63424159200,
63436860000,
63424173600,
63436874400,
14400,
0,
'AMT'
    ],
    [
63436860000,
63455608800,
63436878000,
63455626800,
18000,
1,
'AMST'
    ],
    [
63455608800,
63468309600,
63455623200,
63468324000,
14400,
0,
'AMT'
    ],
    [
63468309600,
63487058400,
63468327600,
63487076400,
18000,
1,
'AMST'
    ],
    [
63487058400,
63500364000,
63487072800,
63500378400,
14400,
0,
'AMT'
    ],
    [
63500364000,
63518508000,
63500382000,
63518526000,
18000,
1,
'AMST'
    ],
    [
63518508000,
63531813600,
63518522400,
63531828000,
14400,
0,
'AMT'
    ],
    [
63531813600,
63549957600,
63531831600,
63549975600,
18000,
1,
'AMST'
    ],
    [
63549957600,
63563263200,
63549972000,
63563277600,
14400,
0,
'AMT'
    ],
    [
63563263200,
63581407200,
63563281200,
63581425200,
18000,
1,
'AMST'
    ],
    [
63581407200,
63594712800,
63581421600,
63594727200,
14400,
0,
'AMT'
    ],
    [
63594712800,
63613461600,
63594730800,
63613479600,
18000,
1,
'AMST'
    ],
    [
63613461600,
63626162400,
63613476000,
63626176800,
14400,
0,
'AMT'
    ],
    [
63626162400,
63644911200,
63626180400,
63644929200,
18000,
1,
'AMST'
    ],
    [
63644911200,
63657612000,
63644925600,
63657626400,
14400,
0,
'AMT'
    ],
    [
63657612000,
63676360800,
63657630000,
63676378800,
18000,
1,
'AMST'
    ],
    [
63676360800,
63689666400,
63676375200,
63689680800,
14400,
0,
'AMT'
    ],
    [
63689666400,
63707810400,
63689684400,
63707828400,
18000,
1,
'AMST'
    ],
    [
63707810400,
63721116000,
63707824800,
63721130400,
14400,
0,
'AMT'
    ],
    [
63721116000,
63739260000,
63721134000,
63739278000,
18000,
1,
'AMST'
    ],
];

sub olson_version { '2009c' }

sub has_dst_changes { 40 }

sub _max_year { 2019 }

sub _new_instance
{
    return shift->_init( @_, spans => $spans );
}

sub _last_offset { 14400 }

my $last_observance = bless( {
  'format' => 'AM%sT',
  'gmtoff' => '4:00',
  'local_start_datetime' => bless( {
    'formatter' => undef,
    'local_rd_days' => 729025,
    'local_rd_secs' => 0,
    'offset_modifier' => 0,
    'rd_nanosecs' => 0,
    'tz' => bless( {
      'name' => 'floating',
      'offset' => 0
    }, 'DateTime::TimeZone::Floating' ),
    'utc_rd_days' => 729025,
    'utc_rd_secs' => 0,
    'utc_year' => 1998
  }, 'DateTime' ),
  'offset_from_std' => 0,
  'offset_from_utc' => 14400,
  'until' => [],
  'utc_start_datetime' => bless( {
    'formatter' => undef,
    'local_rd_days' => 729024,
    'local_rd_secs' => 72000,
    'offset_modifier' => 0,
    'rd_nanosecs' => 0,
    'tz' => bless( {
      'name' => 'floating',
      'offset' => 0
    }, 'DateTime::TimeZone::Floating' ),
    'utc_rd_days' => 729024,
    'utc_rd_secs' => 72000,
    'utc_year' => 1997
  }, 'DateTime' )
}, 'DateTime::TimeZone::OlsonDB::Observance' )
;
sub _last_observance { $last_observance }

my $rules = [
  bless( {
    'at' => '2:00s',
    'from' => '1996',
    'in' => 'Oct',
    'letter' => '',
    'name' => 'RussiaAsia',
    'offset_from_std' => 0,
    'on' => 'lastSun',
    'save' => '0',
    'to' => 'max',
    'type' => undef
  }, 'DateTime::TimeZone::OlsonDB::Rule' ),
  bless( {
    'at' => '2:00s',
    'from' => '1993',
    'in' => 'Mar',
    'letter' => 'S',
    'name' => 'RussiaAsia',
    'offset_from_std' => 3600,
    'on' => 'lastSun',
    'save' => '1:00',
    'to' => 'max',
    'type' => undef
  }, 'DateTime::TimeZone::OlsonDB::Rule' )
]
;
sub _rules { $rules }


1;

