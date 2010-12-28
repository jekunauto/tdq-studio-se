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
# This file was generated from the source file am.xml
# The source file version number was 1.77, generated on
# 2008/06/26 03:47:57.
#
# Do not edit this file directly.
#
###########################################################################

package DateTime::Locale::am;

use strict;
use warnings;
use utf8;

use base 'DateTime::Locale::root';

sub cldr_version { return "1\.6\.1" }

{
    my $am_pm_abbreviated = [ "ጡዋት", "ከሳዓት" ];
    sub am_pm_abbreviated { return $am_pm_abbreviated }
}
{
    my $date_format_full = "EEEE፣\ dd\ MMMM\ ቀን\ yyyy\ G";
    sub date_format_full { return $date_format_full }
}

{
    my $date_format_long = "dd\ MMMM\ yyyy";
    sub date_format_long { return $date_format_long }
}

{
    my $date_format_medium = "MMM\ d\ yyyy";
    sub date_format_medium { return $date_format_medium }
}

{
    my $date_format_short = "dd\/MM\/yy";
    sub date_format_short { return $date_format_short }
}

{
    my $datetime_format = "\{1\}\ \{0\}";
    sub datetime_format { return $datetime_format }
}

{
    my $day_format_abbreviated = [ "ሰኞ", "ማክሰ", "ረቡዕ", "ሐሙስ", "ዓርብ", "ቅዳሜ", "እሑድ" ];
    sub day_format_abbreviated { return $day_format_abbreviated }
}

sub day_format_narrow { $_[0]->day_stand_alone_narrow() }

{
    my $day_format_wide = [ "ሰኞ", "ማክሰኞ", "ረቡዕ", "ሐሙስ", "ዓርብ", "ቅዳሜ", "እሑድ" ];
    sub day_format_wide { return $day_format_wide }
}

sub day_stand_alone_abbreviated { $_[0]->day_format_abbreviated() }

{
    my $day_stand_alone_narrow = [ "ሰ", "ማ", "ረ", "ሐ", "ዓ", "ቅ", "እ" ];
    sub day_stand_alone_narrow { return $day_stand_alone_narrow }
}

sub day_stand_alone_wide { $_[0]->day_format_wide() }

{
    my $era_abbreviated = [ "ዓ\/ዓ", "ዓ\/ም" ];
    sub era_abbreviated { return $era_abbreviated }
}

sub era_narrow { $_[0]->era_abbreviated() }

{
    my $first_day_of_week = 1;
    sub first_day_of_week { return $first_day_of_week }
}

{
    my $month_format_abbreviated = [ "ጃንዩ", "ፌብሩ", "ማርች", "ኤፕረ", "ሜይ", "ጁን", "ጁላይ", "ኦገስ", "ሴፕቴ", "ኦክተ", "ኖቬም", "ዲሴም" ];
    sub month_format_abbreviated { return $month_format_abbreviated }
}

sub month_format_narrow { $_[0]->month_stand_alone_narrow() }

{
    my $month_format_wide = [ "ጃንዩወሪ", "ፌብሩወሪ", "ማርች", "ኤፕረል", "ሜይ", "ጁን", "ጁላይ", "ኦገስት", "ሴፕቴምበር", "ኦክተውበር", "ኖቬምበር", "ዲሴምበር" ];
    sub month_format_wide { return $month_format_wide }
}

sub month_stand_alone_abbreviated { $_[0]->month_format_abbreviated() }

{
    my $month_stand_alone_narrow = [ "ጃ", "ፌ", "ማ", "ኤ", "ሜ", "ጁ", "ጁ", "ኦ", "ሴ", "ኦ", "ኖ", "ዲ" ];
    sub month_stand_alone_narrow { return $month_stand_alone_narrow }
}

sub month_stand_alone_wide { $_[0]->month_format_wide() }

{
    my $quarter_format_abbreviated = [ "Q1", "Q2", "Q3", "Q4" ];
    sub quarter_format_abbreviated { return $quarter_format_abbreviated }
}
{
    my $quarter_format_wide = [ "Q1", "Q2", "Q3", "Q4" ];
    sub quarter_format_wide { return $quarter_format_wide }
}

sub quarter_stand_alone_abbreviated { $_[0]->quarter_format_abbreviated() }


sub quarter_stand_alone_wide { $_[0]->quarter_format_wide() }

{
    my $time_format_full = "hh\:mm\:ss\ a\ v";
    sub time_format_full { return $time_format_full }
}

{
    my $time_format_long = "hh\:mm\:ss\ a\ z";
    sub time_format_long { return $time_format_long }
}

{
    my $time_format_medium = "h\:mm\:ss\ a";
    sub time_format_medium { return $time_format_medium }
}

{
    my $time_format_short = "h\:mm\ a";
    sub time_format_short { return $time_format_short }
}

{
    my $_format_for_MMMMd = "MMMM\ d";
    sub _format_for_MMMMd { return $_format_for_MMMMd }
}

{
    my $_format_for_MMMMdd = "dd\ MMMM";
    sub _format_for_MMMMdd { return $_format_for_MMMMdd }
}

{
    my $_format_for_MMdd = "dd\/MM";
    sub _format_for_MMdd { return $_format_for_MMdd }
}

{
    my $_format_for_yyMM = "MM\/yy";
    sub _format_for_yyMM { return $_format_for_yyMM }
}

{
    my $_format_for_yyQ = "Q\ yy";
    sub _format_for_yyQ { return $_format_for_yyQ }
}

{
    my $_format_for_yyyyMMMM = "MMMM\ yyyy";
    sub _format_for_yyyyMMMM { return $_format_for_yyyyMMMM }
}

{
    my $_available_formats =
        {
          "MMMMd" => "MMMM\ d",
          "MMMMdd" => "dd\ MMMM",
          "MMdd" => "dd\/MM",
          "yyMM" => "MM\/yy",
          "yyQ" => "Q\ yy",
          "yyyyMMMM" => "MMMM\ yyyy"
        };
    sub _available_formats { return $_available_formats }
}

1;

__END__


=pod

=encoding utf8

=head1 NAME

DateTime::Locale::am

=head1 SYNOPSIS

  use DateTime;

  my $dt = DateTime->now( locale => 'am' );
  print $dt->month_name();

=head1 DESCRIPTION

This is the DateTime locale package for Amharic.

=head1 DATA

This locale inherits from the L<DateTime::Locale::root> locale.

It contains the following data.

=head2 Days

=head3 Wide (format)

  ሰኞ
  ማክሰኞ
  ረቡዕ
  ሐሙስ
  ዓርብ
  ቅዳሜ
  እሑድ

=head3 Abbreviated (format)

  ሰኞ
  ማክሰ
  ረቡዕ
  ሐሙስ
  ዓርብ
  ቅዳሜ
  እሑድ

=head3 Narrow (format)

  ሰ
  ማ
  ረ
  ሐ
  ዓ
  ቅ
  እ

=head3 Wide (stand-alone)

  ሰኞ
  ማክሰኞ
  ረቡዕ
  ሐሙስ
  ዓርብ
  ቅዳሜ
  እሑድ

=head3 Abbreviated (stand-alone)

  ሰኞ
  ማክሰ
  ረቡዕ
  ሐሙስ
  ዓርብ
  ቅዳሜ
  እሑድ

=head3 Narrow (stand-alone)

  ሰ
  ማ
  ረ
  ሐ
  ዓ
  ቅ
  እ

=head2 Months

=head3 Wide (format)

  ጃንዩወሪ
  ፌብሩወሪ
  ማርች
  ኤፕረል
  ሜይ
  ጁን
  ጁላይ
  ኦገስት
  ሴፕቴምበር
  ኦክተውበር
  ኖቬምበር
  ዲሴምበር

=head3 Abbreviated (format)

  ጃንዩ
  ፌብሩ
  ማርች
  ኤፕረ
  ሜይ
  ጁን
  ጁላይ
  ኦገስ
  ሴፕቴ
  ኦክተ
  ኖቬም
  ዲሴም

=head3 Narrow (format)

  ጃ
  ፌ
  ማ
  ኤ
  ሜ
  ጁ
  ጁ
  ኦ
  ሴ
  ኦ
  ኖ
  ዲ

=head3 Wide (stand-alone)

  ጃንዩወሪ
  ፌብሩወሪ
  ማርች
  ኤፕረል
  ሜይ
  ጁን
  ጁላይ
  ኦገስት
  ሴፕቴምበር
  ኦክተውበር
  ኖቬምበር
  ዲሴምበር

=head3 Abbreviated (stand-alone)

  ጃንዩ
  ፌብሩ
  ማርች
  ኤፕረ
  ሜይ
  ጁን
  ጁላይ
  ኦገስ
  ሴፕቴ
  ኦክተ
  ኖቬም
  ዲሴም

=head3 Narrow (stand-alone)

  ጃ
  ፌ
  ማ
  ኤ
  ሜ
  ጁ
  ጁ
  ኦ
  ሴ
  ኦ
  ኖ
  ዲ

=head2 Quarters

=head3 Wide (format)

  Q1
  Q2
  Q3
  Q4

=head3 Abbreviated (format)

  Q1
  Q2
  Q3
  Q4

=head3 Narrow (format)

  1
  2
  3
  4

=head3 Wide (stand-alone)

  Q1
  Q2
  Q3
  Q4

=head3 Abbreviated (stand-alone)

  Q1
  Q2
  Q3
  Q4

=head3 Narrow (stand-alone)

  1
  2
  3
  4

=head2 Eras

=head3 Wide

  BCE
  CE

=head3 Abbreviated

  ዓ/ዓ
  ዓ/ም

=head3 Narrow

  ዓ/ዓ
  ዓ/ም

=head2 Date Formats

=head3 Full

   2008-02-05T12:30:30 = ማክሰኞ፣ 05 ፌብሩወሪ ቀን 2008 ዓ/ም
   1995-12-22T09:05:02 = ዓርብ፣ 22 ዲሴምበር ቀን 1995 ዓ/ም
  -0010-09-15T04:44:23 = ቅዳሜ፣ 15 ሴፕቴምበር ቀን -010 ዓ/ዓ

=head3 Long

   2008-02-05T12:30:30 = 05 ፌብሩወሪ 2008
   1995-12-22T09:05:02 = 22 ዲሴምበር 1995
  -0010-09-15T04:44:23 = 15 ሴፕቴምበር -010

=head3 Medium

   2008-02-05T12:30:30 = ፌብሩ 5 2008
   1995-12-22T09:05:02 = ዲሴም 22 1995
  -0010-09-15T04:44:23 = ሴፕቴ 15 -010

=head3 Short

   2008-02-05T12:30:30 = 05/02/8
   1995-12-22T09:05:02 = 22/12/95
  -0010-09-15T04:44:23 = 15/09/10

=head3 Default

   2008-02-05T12:30:30 = ፌብሩ 5 2008
   1995-12-22T09:05:02 = ዲሴም 22 1995
  -0010-09-15T04:44:23 = ሴፕቴ 15 -010

=head2 Time Formats

=head3 Full

   2008-02-05T12:30:30 = 12:30:30 ከሳዓት UTC
   1995-12-22T09:05:02 = 09:05:02 ጡዋት UTC
  -0010-09-15T04:44:23 = 04:44:23 ጡዋት UTC

=head3 Long

   2008-02-05T12:30:30 = 12:30:30 ከሳዓት UTC
   1995-12-22T09:05:02 = 09:05:02 ጡዋት UTC
  -0010-09-15T04:44:23 = 04:44:23 ጡዋት UTC

=head3 Medium

   2008-02-05T12:30:30 = 12:30:30 ከሳዓት
   1995-12-22T09:05:02 = 9:05:02 ጡዋት
  -0010-09-15T04:44:23 = 4:44:23 ጡዋት

=head3 Short

   2008-02-05T12:30:30 = 12:30 ከሳዓት
   1995-12-22T09:05:02 = 9:05 ጡዋት
  -0010-09-15T04:44:23 = 4:44 ጡዋት

=head3 Default

   2008-02-05T12:30:30 = 12:30:30 ከሳዓት
   1995-12-22T09:05:02 = 9:05:02 ጡዋት
  -0010-09-15T04:44:23 = 4:44:23 ጡዋት

=head2 Datetime Formats

=head3 Full

   2008-02-05T12:30:30 = ማክሰኞ፣ 05 ፌብሩወሪ ቀን 2008 ዓ/ም 12:30:30 ከሳዓት UTC
   1995-12-22T09:05:02 = ዓርብ፣ 22 ዲሴምበር ቀን 1995 ዓ/ም 09:05:02 ጡዋት UTC
  -0010-09-15T04:44:23 = ቅዳሜ፣ 15 ሴፕቴምበር ቀን -010 ዓ/ዓ 04:44:23 ጡዋት UTC

=head3 Long

   2008-02-05T12:30:30 = 05 ፌብሩወሪ 2008 12:30:30 ከሳዓት UTC
   1995-12-22T09:05:02 = 22 ዲሴምበር 1995 09:05:02 ጡዋት UTC
  -0010-09-15T04:44:23 = 15 ሴፕቴምበር -010 04:44:23 ጡዋት UTC

=head3 Medium

   2008-02-05T12:30:30 = ፌብሩ 5 2008 12:30:30 ከሳዓት
   1995-12-22T09:05:02 = ዲሴም 22 1995 9:05:02 ጡዋት
  -0010-09-15T04:44:23 = ሴፕቴ 15 -010 4:44:23 ጡዋት

=head3 Short

   2008-02-05T12:30:30 = 05/02/8 12:30 ከሳዓት
   1995-12-22T09:05:02 = 22/12/95 9:05 ጡዋት
  -0010-09-15T04:44:23 = 15/09/10 4:44 ጡዋት

=head3 Default

   2008-02-05T12:30:30 = ፌብሩ 5 2008 12:30:30 ከሳዓት
   1995-12-22T09:05:02 = ዲሴም 22 1995 9:05:02 ጡዋት
  -0010-09-15T04:44:23 = ሴፕቴ 15 -010 4:44:23 ጡዋት

=head2 Available Formats

=head3 Hm (H:mm)

   2008-02-05T12:30:30 = 12:30
   1995-12-22T09:05:02 = 9:05
  -0010-09-15T04:44:23 = 4:44

=head3 M (L)

   2008-02-05T12:30:30 = 2
   1995-12-22T09:05:02 = 12
  -0010-09-15T04:44:23 = 9

=head3 MEd (E, M-d)

   2008-02-05T12:30:30 = ማክሰ, 2-5
   1995-12-22T09:05:02 = ዓርብ, 12-22
  -0010-09-15T04:44:23 = ቅዳሜ, 9-15

=head3 MMM (LLL)

   2008-02-05T12:30:30 = ፌብሩ
   1995-12-22T09:05:02 = ዲሴም
  -0010-09-15T04:44:23 = ሴፕቴ

=head3 MMMEd (E MMM d)

   2008-02-05T12:30:30 = ማክሰ ፌብሩ 5
   1995-12-22T09:05:02 = ዓርብ ዲሴም 22
  -0010-09-15T04:44:23 = ቅዳሜ ሴፕቴ 15

=head3 MMMMEd (E MMMM d)

   2008-02-05T12:30:30 = ማክሰ ፌብሩወሪ 5
   1995-12-22T09:05:02 = ዓርብ ዲሴምበር 22
  -0010-09-15T04:44:23 = ቅዳሜ ሴፕቴምበር 15

=head3 MMMMd (MMMM d)

   2008-02-05T12:30:30 = ፌብሩወሪ 5
   1995-12-22T09:05:02 = ዲሴምበር 22
  -0010-09-15T04:44:23 = ሴፕቴምበር 15

=head3 MMMMdd (dd MMMM)

   2008-02-05T12:30:30 = 05 ፌብሩወሪ
   1995-12-22T09:05:02 = 22 ዲሴምበር
  -0010-09-15T04:44:23 = 15 ሴፕቴምበር

=head3 MMMd (MMM d)

   2008-02-05T12:30:30 = ፌብሩ 5
   1995-12-22T09:05:02 = ዲሴም 22
  -0010-09-15T04:44:23 = ሴፕቴ 15

=head3 MMdd (dd/MM)

   2008-02-05T12:30:30 = 05/02
   1995-12-22T09:05:02 = 22/12
  -0010-09-15T04:44:23 = 15/09

=head3 Md (M-d)

   2008-02-05T12:30:30 = 2-5
   1995-12-22T09:05:02 = 12-22
  -0010-09-15T04:44:23 = 9-15

=head3 d (d)

   2008-02-05T12:30:30 = 5
   1995-12-22T09:05:02 = 22
  -0010-09-15T04:44:23 = 15

=head3 ms (mm:ss)

   2008-02-05T12:30:30 = 30:30
   1995-12-22T09:05:02 = 05:02
  -0010-09-15T04:44:23 = 44:23

=head3 y (yyyy)

   2008-02-05T12:30:30 = 2008
   1995-12-22T09:05:02 = 1995
  -0010-09-15T04:44:23 = -010

=head3 yM (yyyy-M)

   2008-02-05T12:30:30 = 2008-2
   1995-12-22T09:05:02 = 1995-12
  -0010-09-15T04:44:23 = -010-9

=head3 yMEd (EEE, yyyy-M-d)

   2008-02-05T12:30:30 = ማክሰ, 2008-2-5
   1995-12-22T09:05:02 = ዓርብ, 1995-12-22
  -0010-09-15T04:44:23 = ቅዳሜ, -010-9-15

=head3 yMMM (yyyy MMM)

   2008-02-05T12:30:30 = 2008 ፌብሩ
   1995-12-22T09:05:02 = 1995 ዲሴም
  -0010-09-15T04:44:23 = -010 ሴፕቴ

=head3 yMMMEd (EEE, yyyy MMM d)

   2008-02-05T12:30:30 = ማክሰ, 2008 ፌብሩ 5
   1995-12-22T09:05:02 = ዓርብ, 1995 ዲሴም 22
  -0010-09-15T04:44:23 = ቅዳሜ, -010 ሴፕቴ 15

=head3 yMMMM (yyyy MMMM)

   2008-02-05T12:30:30 = 2008 ፌብሩወሪ
   1995-12-22T09:05:02 = 1995 ዲሴምበር
  -0010-09-15T04:44:23 = -010 ሴፕቴምበር

=head3 yQ (yyyy Q)

   2008-02-05T12:30:30 = 2008 1
   1995-12-22T09:05:02 = 1995 4
  -0010-09-15T04:44:23 = -010 3

=head3 yQQQ (yyyy QQQ)

   2008-02-05T12:30:30 = 2008 Q1
   1995-12-22T09:05:02 = 1995 Q4
  -0010-09-15T04:44:23 = -010 Q3

=head3 yyMM (MM/yy)

   2008-02-05T12:30:30 = 02/8
   1995-12-22T09:05:02 = 12/95
  -0010-09-15T04:44:23 = 09/10

=head3 yyQ (Q yy)

   2008-02-05T12:30:30 = 1 8
   1995-12-22T09:05:02 = 4 95
  -0010-09-15T04:44:23 = 3 10

=head3 yyyyMMMM (MMMM yyyy)

   2008-02-05T12:30:30 = ፌብሩወሪ 2008
   1995-12-22T09:05:02 = ዲሴምበር 1995
  -0010-09-15T04:44:23 = ሴፕቴምበር -010

=head2 Miscellaneous

=head3 Prefers 24 hour time?

No

=head3 Local first day of the week

ሰኞ


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
