@rem = '--*-Perl-*--
@echo off
if "%OS%" == "Windows_NT" goto WinNT
perl -x -S "%0" %1 %2 %3 %4 %5 %6 %7 %8 %9
goto endofperl
:WinNT
perl -x -S %0 %*
if NOT "%COMSPEC%" == "%SystemRoot%\system32\cmd.exe" goto endofperl
if %errorlevel% == 9009 echo You do not have Perl in your PATH.
if errorlevel 1 goto script_failed_so_exit_with_non_zero_val 2>nul
goto endofperl
@rem ';
#!/usr/bin/perl
#line 15

eval 'exec /usr/bin/perl -w -S $0 ${1+"$@"}'
    if 0; # not running under some shell

=head1 NAME

geoip-lookup - lookup country for IP address or hostname

=head1 SYNOPSIS

 geoip-lookup [-l] <ipaddress|hostname>

=head1 DESCRIPTION

The I<geoip-lookup> program will return the country for the IP address or
hostname given as the first command line argument.
It queries the GeoIP Country database in C</usr/local/share/GeoIP/GeoIP.dat>.

By default it prints the ISO 3166 country code.  Use the C<-l> option
to print the country name.

=head1 EXAMPLE

Lookup up country for IP address 24.24.24.24

 # geoip-lookup 24.24.24.24
 United States

=head1 AUTHOR

T.J. Mather <tjmather@maxmind.com>

=cut

use strict;

use Geo::IP::PurePerl;
use Getopt::Std;

use Locale::Country;

my %opts;
unless (getopt('', \%opts)) {
  usage();
}

my $addr = shift || usage();

my $gi = Geo::IP::PurePerl->new();

my $code = $gi->country_code_by_name($addr);

if ($opts{l}) {
  eval { require Locale::Country; }
    or die "Use of -l flag requires that Locale::Country is installed";
  print Locale::Country::code2country($code);
} else {
  print $code;
}
print "\n";

sub usage
{
  my $progname = $0;
  $progname =~ s,.*/,,;    # only basename left in progname
    $progname =~ s/\.\w*$//; # strip extension if any
  die "Usage: $progname [-l] <ipaddress|hostname>\n";
}

__END__
:endofperl
