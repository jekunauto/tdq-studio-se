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
# This file was generated from the source file pa_Guru_IN.xml
# The source file version number was 1.12, generated on
# 2008/05/28 15:49:34.
#
# Do not edit this file directly.
#
###########################################################################

package DateTime::Locale::pa_Guru_IN;

use strict;
use warnings;
use utf8;

use base 'DateTime::Locale::pa_Guru';

sub cldr_version { return "1\.6\.1" }

{
    my $first_day_of_week = 1;
    sub first_day_of_week { return $first_day_of_week }
}

1;

__END__


=pod

=encoding utf8

=head1 NAME

DateTime::Locale::pa_Guru_IN

=head1 SYNOPSIS

  use DateTime;

  my $dt = DateTime->now( locale => 'pa_Guru_IN' );
  print $dt->month_name();

=head1 DESCRIPTION

This is the DateTime locale package for Punjabi Gurmukhi India.

=head1 DATA

This locale inherits from the L<DateTime::Locale::pa_Guru> locale.

It contains the following data.

=head2 Days

=head3 Wide (format)

  ਸੋਮਵਾਰ
  ਮੰਗਲਵਾਰ
  ਬੁਧਵਾਰ
  ਵੀਰਵਾਰ
  ਸ਼ੁੱਕਰਵਾਰ
  ਸ਼ਨੀਚਰਵਾਰ
  ਐਤਵਾਰ

=head3 Abbreviated (format)

  ਸੋਮ.
  ਮੰਗਲ.
  ਬੁਧ.
  ਵੀਰ.
  ਸ਼ੁਕਰ.
  ਸ਼ਨੀ.
  ਐਤ.

=head3 Narrow (format)

  ਸੋ
  ਮੰ
  ਬੁੱ
  ਵੀ
  ਸ਼ੁੱ
  ਸ਼
  ਐ

=head3 Wide (stand-alone)

  ਸੋਮਵਾਰ
  ਮੰਗਲਵਾਰ
  ਬੁਧਵਾਰ
  ਵੀਰਵਾਰ
  ਸ਼ੁੱਕਰਵਾਰ
  ਸ਼ਨੀਚਰਵਾਰ
  ਐਤਵਾਰ

=head3 Abbreviated (stand-alone)

  ਸੋਮ.
  ਮੰਗਲ.
  ਬੁਧ.
  ਵੀਰ.
  ਸ਼ੁਕਰ.
  ਸ਼ਨੀ.
  ਐਤ.

=head3 Narrow (stand-alone)

  ਸੋ
  ਮੰ
  ਬੁੱ
  ਵੀ
  ਸ਼ੁੱ
  ਸ਼
  ਐ

=head2 Months

=head3 Wide (format)

  ਜਨਵਰੀ
  ਫ਼ਰਵਰੀ
  ਮਾਰਚ
  ਅਪ੍ਰੈਲ
  ਮਈ
  ਜੂਨ
  ਜੁਲਾਈ
  ਅਗਸਤ
  ਸਤੰਬਰ
  ਅਕਤੂਬਰ
  ਨਵੰਬਰ
  ਦਸੰਬਰ

=head3 Abbreviated (format)

  ਜਨਵਰੀ
  ਫ਼ਰਵਰੀ
  ਮਾਰਚ
  ਅਪ੍ਰੈਲ
  ਮਈ
  ਜੂਨ
  ਜੁਲਾਈ
  ਅਗਸਤ
  ਸਤੰਬਰ
  ਅਕਤੂਬਰ
  ਨਵੰਬਰ
  ਦਸੰਬਰ

=head3 Narrow (format)

  ਜ
  ਫ
  ਮਾ
  ਅ
  ਮ
  ਜੂ
  ਜੁ
  ਅ
  ਸ
  ਅ
  ਨ
  ਦ

=head3 Wide (stand-alone)

  ਜਨਵਰੀ
  ਫ਼ਰਵਰੀ
  ਮਾਰਚ
  ਅਪ੍ਰੈਲ
  ਮਈ
  ਜੂਨ
  ਜੁਲਾਈ
  ਅਗਸਤ
  ਸਤੰਬਰ
  ਅਕਤੂਬਰ
  ਨਵੰਬਰ
  ਦਸੰਬਰ

=head3 Abbreviated (stand-alone)

  ਜਨਵਰੀ
  ਫ਼ਰਵਰੀ
  ਮਾਰਚ
  ਅਪ੍ਰੈਲ
  ਮਈ
  ਜੂਨ
  ਜੁਲਾਈ
  ਅਗਸਤ
  ਸਤੰਬਰ
  ਅਕਤੂਬਰ
  ਨਵੰਬਰ
  ਦਸੰਬਰ

=head3 Narrow (stand-alone)

  ਜ
  ਫ
  ਮਾ
  ਅ
  ਮ
  ਜੂ
  ਜੁ
  ਅ
  ਸ
  ਅ
  ਨ
  ਦ

=head2 Quarters

=head3 Wide (format)

  ਪਹਿਲਾਂ ਚੌਥਾਈ
  ਦੂਜਾ ਚੌਥਾਈ
  ਤੀਜਾ ਚੌਥਾਈ
  ਚੌਥਾ ਚੌਥਾਈ

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

  ਪਹਿਲਾਂ ਚੌਥਾਈ
  ਦੂਜਾ ਚੌਥਾਈ
  ਤੀਜਾ ਚੌਥਾਈ
  ਚੌਥਾ ਚੌਥਾਈ

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

  ਈਸਾਪੂਰਵ
  ਸੰਨ

=head3 Abbreviated

  BCE
  CE

=head3 Narrow

  BCE
  CE

=head2 Date Formats

=head3 Full

   2008-02-05T12:30:30 = ਮੰਗਲਵਾਰ, 05 ਫ਼ਰਵਰੀ 2008
   1995-12-22T09:05:02 = ਸ਼ੁੱਕਰਵਾਰ, 22 ਦਸੰਬਰ 1995
  -0010-09-15T04:44:23 = ਸ਼ਨੀਚਰਵਾਰ, 15 ਸਤੰਬਰ -010

=head3 Long

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ 15

=head3 Medium

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ 15

=head3 Short

   2008-02-05T12:30:30 = 05/02/2008
   1995-12-22T09:05:02 = 22/12/1995
  -0010-09-15T04:44:23 = 15/09/-10

=head3 Default

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ 15

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

   2008-02-05T12:30:30 = ਮੰਗਲਵਾਰ, 05 ਫ਼ਰਵਰੀ 2008 12:30:30 UTC
   1995-12-22T09:05:02 = ਸ਼ੁੱਕਰਵਾਰ, 22 ਦਸੰਬਰ 1995 09:05:02 UTC
  -0010-09-15T04:44:23 = ਸ਼ਨੀਚਰਵਾਰ, 15 ਸਤੰਬਰ -010 04:44:23 UTC

=head3 Long

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ 5 12:30:30 UTC
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ 22 09:05:02 UTC
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ 15 04:44:23 UTC

=head3 Medium

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ 5 12:30:30
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ 22 09:05:02
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ 15 04:44:23

=head3 Short

   2008-02-05T12:30:30 = 05/02/2008 12:30
   1995-12-22T09:05:02 = 22/12/1995 09:05
  -0010-09-15T04:44:23 = 15/09/-10 04:44

=head3 Default

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ 5 12:30:30
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ 22 09:05:02
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ 15 04:44:23

=head2 Available Formats

=head3 HHmmss (HH:mm:ss 	13:25:59)

   2008-02-05T12:30:30 = 12:30:30 	13:25:59
   1995-12-22T09:05:02 = 09:05:02 	13:25:59
  -0010-09-15T04:44:23 = 04:44:23 	13:25:59

=head3 Hm (H:mm)

   2008-02-05T12:30:30 = 12:30
   1995-12-22T09:05:02 = 9:05
  -0010-09-15T04:44:23 = 4:44

=head3 M (L)

   2008-02-05T12:30:30 = 2
   1995-12-22T09:05:02 = 12
  -0010-09-15T04:44:23 = 9

=head3 MEd (E, M-d)

   2008-02-05T12:30:30 = ਮੰਗਲ., 2-5
   1995-12-22T09:05:02 = ਸ਼ੁਕਰ., 12-22
  -0010-09-15T04:44:23 = ਸ਼ਨੀ., 9-15

=head3 MMM (LLL)

   2008-02-05T12:30:30 = ਫ਼ਰਵਰੀ
   1995-12-22T09:05:02 = ਦਸੰਬਰ
  -0010-09-15T04:44:23 = ਸਤੰਬਰ

=head3 MMMEd (E MMM d)

   2008-02-05T12:30:30 = ਮੰਗਲ. ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = ਸ਼ੁਕਰ. ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = ਸ਼ਨੀ. ਸਤੰਬਰ 15

=head3 MMMMEd (E MMMM d)

   2008-02-05T12:30:30 = ਮੰਗਲ. ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = ਸ਼ੁਕਰ. ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = ਸ਼ਨੀ. ਸਤੰਬਰ 15

=head3 MMMMd (MMMM d)

   2008-02-05T12:30:30 = ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = ਸਤੰਬਰ 15

=head3 MMMd (MMM d)

   2008-02-05T12:30:30 = ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = ਸਤੰਬਰ 15

=head3 Md (d/M)

   2008-02-05T12:30:30 = 5/2
   1995-12-22T09:05:02 = 22/12
  -0010-09-15T04:44:23 = 15/9

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

=head3 yM (yyyy-M)

   2008-02-05T12:30:30 = 2008-2
   1995-12-22T09:05:02 = 1995-12
  -0010-09-15T04:44:23 = -010-9

=head3 yMEd (EEE, yyyy-M-d)

   2008-02-05T12:30:30 = ਮੰਗਲ., 2008-2-5
   1995-12-22T09:05:02 = ਸ਼ੁਕਰ., 1995-12-22
  -0010-09-15T04:44:23 = ਸ਼ਨੀ., -010-9-15

=head3 yMMM (yyyy MMM)

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ

=head3 yMMMEd (EEE, yyyy MMM d)

   2008-02-05T12:30:30 = ਮੰਗਲ., 2008 ਫ਼ਰਵਰੀ 5
   1995-12-22T09:05:02 = ਸ਼ੁਕਰ., 1995 ਦਸੰਬਰ 22
  -0010-09-15T04:44:23 = ਸ਼ਨੀ., -010 ਸਤੰਬਰ 15

=head3 yMMMM (yyyy MMMM)

   2008-02-05T12:30:30 = 2008 ਫ਼ਰਵਰੀ
   1995-12-22T09:05:02 = 1995 ਦਸੰਬਰ
  -0010-09-15T04:44:23 = -010 ਸਤੰਬਰ

=head3 yQ (yyyy Q)

   2008-02-05T12:30:30 = 2008 1
   1995-12-22T09:05:02 = 1995 4
  -0010-09-15T04:44:23 = -010 3

=head3 yQQQ (yyyy QQQ)

   2008-02-05T12:30:30 = 2008 Q1
   1995-12-22T09:05:02 = 1995 Q4
  -0010-09-15T04:44:23 = -010 Q3

=head3 yyMMM (MMM yy)

   2008-02-05T12:30:30 = ਫ਼ਰਵਰੀ 8
   1995-12-22T09:05:02 = ਦਸੰਬਰ 95
  -0010-09-15T04:44:23 = ਸਤੰਬਰ 10

=head3 yyQ (Q yy)

   2008-02-05T12:30:30 = 1 8
   1995-12-22T09:05:02 = 4 95
  -0010-09-15T04:44:23 = 3 10

=head2 Miscellaneous

=head3 Prefers 24 hour time?

Yes

=head3 Local first day of the week

ਸੋਮਵਾਰ


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
