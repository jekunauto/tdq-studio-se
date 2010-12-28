###########################################################################
#
# This file is auto-generated by the Perl DateTime Suite locale
# generator (0.05).  This code generator comes with the
# DateTime::Locale distribution in the tools/ directory, and is called
# generate-from-cldr.
#
# This file as generated from the CLDR XML locale data.  See the
# LICENSE.cldr file included in this distribution for license details.
#
# This file was generated from the source file nl_BE.xml
# The source file version number was 1.65, generated on
# 2008/06/17 14:12:12.
#
# Do not edit this file directly.
#
###########################################################################

package DateTime::Locale::nl_BE;

use strict;
use warnings;
use utf8;

use base 'DateTime::Locale::nl';

sub cldr_version { return "1\.6\.1" }

{
    my $date_format_medium = "d\-MMM\-yyyy";
    sub date_format_medium { return $date_format_medium }
}

{
    my $date_format_short = "d\/MM\/yy";
    sub date_format_short { return $date_format_short }
}

{
    my $first_day_of_week = 1;
    sub first_day_of_week { return $first_day_of_week }
}

1;

__END__


=pod

=encoding utf8

=head1 NAME

DateTime::Locale::nl_BE

=head1 SYNOPSIS

  use DateTime;

  my $dt = DateTime->now( locale => 'nl_BE' );
  print $dt->month_name();

=head1 DESCRIPTION

This is the DateTime locale package for Dutch Belgium.

=head1 DATA

This locale inherits from the L<DateTime::Locale::nl> locale.

It contains the following data.

=head2 Days

=head3 Wide (format)

  maandag
  dinsdag
  woensdag
  donderdag
  vrijdag
  zaterdag
  zondag

=head3 Abbreviated (format)

  ma
  di
  wo
  do
  vr
  za
  zo

=head3 Narrow (format)

  M
  D
  W
  D
  V
  Z
  Z

=head3 Wide (stand-alone)

  maandag
  dinsdag
  woensdag
  donderdag
  vrijdag
  zaterdag
  zondag

=head3 Abbreviated (stand-alone)

  ma
  di
  wo
  do
  vr
  za
  zo

=head3 Narrow (stand-alone)

  M
  D
  W
  D
  V
  Z
  Z

=head2 Months

=head3 Wide (format)

  januari
  februari
  maart
  april
  mei
  juni
  juli
  augustus
  september
  oktober
  november
  december

=head3 Abbreviated (format)

  jan
  feb
  mrt
  apr
  mei
  jun
  jul
  aug
  sep
  okt
  nov
  dec

=head3 Narrow (format)

  J
  F
  M
  A
  M
  J
  J
  A
  S
  O
  N
  D

=head3 Wide (stand-alone)

  januari
  februari
  maart
  april
  mei
  juni
  juli
  augustus
  september
  oktober
  november
  december

=head3 Abbreviated (stand-alone)

  jan
  feb
  mrt
  apr
  mei
  jun
  jul
  aug
  sep
  okt
  nov
  dec

=head3 Narrow (stand-alone)

  J
  F
  M
  A
  M
  J
  J
  A
  S
  O
  N
  D

=head2 Quarters

=head3 Wide (format)

  1e kwartaal
  2e kwartaal
  3e kwartaal
  4e kwartaal

=head3 Abbreviated (format)

  K1
  K2
  K3
  K4

=head3 Narrow (format)

  1
  2
  3
  4

=head3 Wide (stand-alone)

  1e kwartaal
  2e kwartaal
  3e kwartaal
  4e kwartaal

=head3 Abbreviated (stand-alone)

  K1
  K2
  K3
  K4

=head3 Narrow (stand-alone)

  1
  2
  3
  4

=head2 Eras

=head3 Wide

  Voor Christus
  Anno Domini

=head3 Abbreviated

  v. Chr.
  n. Chr.

=head3 Narrow

  v. Chr.
  n. Chr.

=head2 Date Formats

=head3 Full

   2008-02-05T12:30:30 = dinsdag 5 februari 2008
   1995-12-22T09:05:02 = vrijdag 22 december 1995
  -0010-09-15T04:44:23 = zaterdag 15 september -010

=head3 Long

   2008-02-05T12:30:30 = 5 februari 2008
   1995-12-22T09:05:02 = 22 december 1995
  -0010-09-15T04:44:23 = 15 september -010

=head3 Medium

   2008-02-05T12:30:30 = 5-feb-2008
   1995-12-22T09:05:02 = 22-dec-1995
  -0010-09-15T04:44:23 = 15-sep--010

=head3 Short

   2008-02-05T12:30:30 = 5/02/8
   1995-12-22T09:05:02 = 22/12/95
  -0010-09-15T04:44:23 = 15/09/10

=head3 Default

   2008-02-05T12:30:30 = 5-feb-2008
   1995-12-22T09:05:02 = 22-dec-1995
  -0010-09-15T04:44:23 = 15-sep--010

=head2 Time Formats

=head3 Full

   2008-02-05T12:30:30 = 12:30:30 UTC
   1995-12-22T09:05:02 = 09:05:02 UTC
  -0010-09-15T04:44:23 = 04:44:23 UTC

=head3 Long

   2008-02-05T12:30:30 = 12:30:30 UTC
   1995-12-22T09:05:02 = 09:05:02 UTC
  -0010-09-15T04:44:23 = 04:44:23 UTC

=head3 Medium

   2008-02-05T12:30:30 = 12:30:30
   1995-12-22T09:05:02 = 09:05:02
  -0010-09-15T04:44:23 = 04:44:23

=head3 Short

   2008-02-05T12:30:30 = 12:30
   1995-12-22T09:05:02 = 09:05
  -0010-09-15T04:44:23 = 04:44

=head3 Default

   2008-02-05T12:30:30 = 12:30:30
   1995-12-22T09:05:02 = 09:05:02
  -0010-09-15T04:44:23 = 04:44:23

=head2 Datetime Formats

=head3 Full

   2008-02-05T12:30:30 = dinsdag 5 februari 2008 12:30:30 UTC
   1995-12-22T09:05:02 = vrijdag 22 december 1995 09:05:02 UTC
  -0010-09-15T04:44:23 = zaterdag 15 september -010 04:44:23 UTC

=head3 Long

   2008-02-05T12:30:30 = 5 februari 2008 12:30:30 UTC
   1995-12-22T09:05:02 = 22 december 1995 09:05:02 UTC
  -0010-09-15T04:44:23 = 15 september -010 04:44:23 UTC

=head3 Medium

   2008-02-05T12:30:30 = 5-feb-2008 12:30:30
   1995-12-22T09:05:02 = 22-dec-1995 09:05:02
  -0010-09-15T04:44:23 = 15-sep--010 04:44:23

=head3 Short

   2008-02-05T12:30:30 = 5/02/8 12:30
   1995-12-22T09:05:02 = 22/12/95 09:05
  -0010-09-15T04:44:23 = 15/09/10 04:44

=head3 Default

   2008-02-05T12:30:30 = 5-feb-2008 12:30:30
   1995-12-22T09:05:02 = 22-dec-1995 09:05:02
  -0010-09-15T04:44:23 = 15-sep--010 04:44:23

=head2 Available Formats

=head3 Hm (HH:mm)

   2008-02-05T12:30:30 = 12:30
   1995-12-22T09:05:02 = 09:05
  -0010-09-15T04:44:23 = 04:44

=head3 M (L)

   2008-02-05T12:30:30 = 2
   1995-12-22T09:05:02 = 12
  -0010-09-15T04:44:23 = 9

=head3 MEd (E, d-M)

   2008-02-05T12:30:30 = di, 5-2
   1995-12-22T09:05:02 = vr, 22-12
  -0010-09-15T04:44:23 = za, 15-9

=head3 MMM (LLL)

   2008-02-05T12:30:30 = feb
   1995-12-22T09:05:02 = dec
  -0010-09-15T04:44:23 = sep

=head3 MMMEd (E d MMM)

   2008-02-05T12:30:30 = di 5 feb
   1995-12-22T09:05:02 = vr 22 dec
  -0010-09-15T04:44:23 = za 15 sep

=head3 MMMMEd (E d MMMM)

   2008-02-05T12:30:30 = di 5 februari
   1995-12-22T09:05:02 = vr 22 december
  -0010-09-15T04:44:23 = za 15 september

=head3 MMMMd (d MMMM)

   2008-02-05T12:30:30 = 5 februari
   1995-12-22T09:05:02 = 22 december
  -0010-09-15T04:44:23 = 15 september

=head3 MMMd (d-MMM)

   2008-02-05T12:30:30 = 5-feb
   1995-12-22T09:05:02 = 22-dec
  -0010-09-15T04:44:23 = 15-sep

=head3 MMd (d-MM)

   2008-02-05T12:30:30 = 5-02
   1995-12-22T09:05:02 = 22-12
  -0010-09-15T04:44:23 = 15-09

=head3 MMdd (dd-MM)

   2008-02-05T12:30:30 = 05-02
   1995-12-22T09:05:02 = 22-12
  -0010-09-15T04:44:23 = 15-09

=head3 Md (d-M)

   2008-02-05T12:30:30 = 5-2
   1995-12-22T09:05:02 = 22-12
  -0010-09-15T04:44:23 = 15-9

=head3 d (d)

   2008-02-05T12:30:30 = 5
   1995-12-22T09:05:02 = 22
  -0010-09-15T04:44:23 = 15

=head3 mmss (mm:ss)

   2008-02-05T12:30:30 = 30:30
   1995-12-22T09:05:02 = 05:02
  -0010-09-15T04:44:23 = 44:23

=head3 ms (mm:ss)

   2008-02-05T12:30:30 = 30:30
   1995-12-22T09:05:02 = 05:02
  -0010-09-15T04:44:23 = 44:23

=head3 y (yyyy)

   2008-02-05T12:30:30 = 2008
   1995-12-22T09:05:02 = 1995
  -0010-09-15T04:44:23 = -010

=head3 yM (M-yyyy)

   2008-02-05T12:30:30 = 2-2008
   1995-12-22T09:05:02 = 12-1995
  -0010-09-15T04:44:23 = 9--010

=head3 yMEd (EEE, d-M-yyyy)

   2008-02-05T12:30:30 = di, 5-2-2008
   1995-12-22T09:05:02 = vr, 22-12-1995
  -0010-09-15T04:44:23 = za, 15-9--010

=head3 yMMM (MMM yyyy)

   2008-02-05T12:30:30 = feb 2008
   1995-12-22T09:05:02 = dec 1995
  -0010-09-15T04:44:23 = sep -010

=head3 yMMMEd (EEE, d MMM yyyy)

   2008-02-05T12:30:30 = di, 5 feb 2008
   1995-12-22T09:05:02 = vr, 22 dec 1995
  -0010-09-15T04:44:23 = za, 15 sep -010

=head3 yMMMM (MMMM yyyy)

   2008-02-05T12:30:30 = februari 2008
   1995-12-22T09:05:02 = december 1995
  -0010-09-15T04:44:23 = september -010

=head3 yQ (Q yyyy)

   2008-02-05T12:30:30 = 1 2008
   1995-12-22T09:05:02 = 4 1995
  -0010-09-15T04:44:23 = 3 -010

=head3 yQQQ (QQQ yyyy)

   2008-02-05T12:30:30 = K1 2008
   1995-12-22T09:05:02 = K4 1995
  -0010-09-15T04:44:23 = K3 -010

=head3 yyMM (MM-yy)

   2008-02-05T12:30:30 = 02-8
   1995-12-22T09:05:02 = 12-95
  -0010-09-15T04:44:23 = 09-10

=head3 yyMMM (MMM yy)

   2008-02-05T12:30:30 = feb 8
   1995-12-22T09:05:02 = dec 95
  -0010-09-15T04:44:23 = sep 10

=head3 yyQ (Q yy)

   2008-02-05T12:30:30 = 1 8
   1995-12-22T09:05:02 = 4 95
  -0010-09-15T04:44:23 = 3 10

=head3 yyQQQQ (QQQQ yy)

   2008-02-05T12:30:30 = 1e kwartaal 8
   1995-12-22T09:05:02 = 4e kwartaal 95
  -0010-09-15T04:44:23 = 3e kwartaal 10

=head3 yyyyMMMM (MMMM yyyy)

   2008-02-05T12:30:30 = februari 2008
   1995-12-22T09:05:02 = december 1995
  -0010-09-15T04:44:23 = september -010

=head2 Miscellaneous

=head3 Prefers 24 hour time?

Yes

=head3 Local first day of the week

maandag


=head1 SUPPORT

See L<DateTime::Locale>.

=head1 AUTHOR

Dave Rolsky <autarch@urth.org>

=head1 COPYRIGHT

Copyright (c) 2008 David Rolsky. All rights reserved. This program is
free software; you can redistribute it and/or modify it under the same
terms as Perl itself.

This module was generated from data provided by the CLDR project, see
the LICENSE.cldr in this distribution for details on the CLDR data's
license.

=cut
