# This file is auto-generated by the Perl DateTime Suite time zone
# code generator (0.07) This code generator comes with the
# DateTime::TimeZone module distribution in the tools/ directory

#
# Generated from /tmp/GaPAi6WTRf/africa.  Olson data version 2009c
#
# Do not edit this file directly.
#
package DateTime::TimeZone::Africa::Casablanca;

use strict;

use Class::Singleton;
use DateTime::TimeZone;
use DateTime::TimeZone::OlsonDB;

@DateTime::TimeZone::Africa::Casablanca::ISA = ( 'Class::Singleton', 'DateTime::TimeZone' );

my $spans =
[
    [
DateTime::TimeZone::NEG_INFINITY,
60362670620,
DateTime::TimeZone::NEG_INFINITY,
60362668800,
-1820,
0,
'LMT'
    ],
    [
60362670620,
61179321600,
60362670620,
61179321600,
0,
0,
'WET'
    ],
    [
61179321600,
61185193200,
61179325200,
61185196800,
3600,
1,
'WEST'
    ],
    [
61185193200,
61193664000,
61185193200,
61193664000,
0,
0,
'WET'
    ],
    [
61193664000,
61374495600,
61193667600,
61374499200,
3600,
1,
'WEST'
    ],
    [
61374495600,
61518441600,
61374495600,
61518441600,
0,
0,
'WET'
    ],
    [
61518441600,
61530534000,
61518445200,
61530537600,
3600,
1,
'WEST'
    ],
    [
61530534000,
62054251200,
61530534000,
62054251200,
0,
0,
'WET'
    ],
    [
62054251200,
62064572400,
62054254800,
62064576000,
3600,
1,
'WEST'
    ],
    [
62064572400,
62276947200,
62064572400,
62276947200,
0,
0,
'WET'
    ],
    [
62276947200,
62282905200,
62276950800,
62282908800,
3600,
1,
'WEST'
    ],
    [
62282905200,
62335440000,
62282905200,
62335440000,
0,
0,
'WET'
    ],
    [
62335440000,
62343385200,
62335443600,
62343388800,
3600,
1,
'WEST'
    ],
    [
62343385200,
62366976000,
62343385200,
62366976000,
0,
0,
'WET'
    ],
    [
62366976000,
62379932400,
62366979600,
62379936000,
3600,
1,
'WEST'
    ],
    [
62379932400,
62401190400,
62379932400,
62401190400,
0,
0,
'WET'
    ],
    [
62401190400,
62406716400,
62401194000,
62406720000,
3600,
1,
'WEST'
    ],
    [
62406716400,
62583926400,
62406716400,
62583926400,
0,
0,
'WET'
    ],
    [
62583926400,
62640601200,
62583930000,
62640604800,
3600,
0,
'CET'
    ],
    [
62640601200,
63347961600,
62640601200,
63347961600,
0,
0,
'WET'
    ],
    [
63347961600,
63355906800,
63347965200,
63355910400,
3600,
1,
'WEST'
    ],
    [
63355906800,
DateTime::TimeZone::INFINITY,
63355906800,
DateTime::TimeZone::INFINITY,
0,
0,
'WET'
    ],
];

sub olson_version { '2009c' }

sub has_dst_changes { 9 }

sub _max_year { 2019 }

sub _new_instance
{
    return shift->_init( @_, spans => $spans );
}



1;

