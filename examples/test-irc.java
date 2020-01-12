/*
 * Copyright (c) 2020 Alexey Y. Woronov
 */

import java.lang.*;
import javairciot.jlayerirc;

class test_IRC extends javairciot.jlayerirc {

  public static void irc_testing() {
    System.out.print("This is IRC-IoT testing library, ");
    System.out.println("JLayerIRC (RFC-1459) class");
    System.out.print("Library version: ");
    System.out.print(CONST.irciot_library_version);
    System.out.print(", IRC-IoT protocol version: ");
    System.out.println(CONST.irciot_protocol_version);
  };

  public static void irc_checks() {
    jlayerirc my_irc = new jlayerirc();
    String my_str = "127.0.0.1";
    System.out.print("\nTest is_ipv4_address_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_ipv4_address_(my_str));
    my_str = "235.345.63.1";
    System.out.print("Test is_ipv4_address_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_ipv4_address_(my_str));
    my_str = "2a00:1450:4010:c06::8b";
    System.out.print("\nTest is_ipv6_address_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_ipv6_address_(my_str));
    my_str = "2a00:1450:4010:c06::8z";
    System.out.print("Test is_ipv6_address_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_ipv6_address_(my_str));
    my_str = "Robot";
    System.out.print("\nTest is_irc_nick_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_irc_nick_(my_str));
    my_str = "##!*(";
    System.out.print("Test is_irc_nick_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_irc_nick_(my_str));
    my_str = "#channel2020";
    System.out.print("\nTest is_irc_channel_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_irc_channel_(my_str));
    my_str = "32r234r#_you_";
    System.out.print("Test is_irc_channel_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_irc_channel_(my_str));
    System.out.print("\nTest irc_random_nick_(\"Test\", false) = \"");
    int my_int = my_irc.irc_random_nick_("Test", false);
    System.out.println(my_irc.irc_nick_try + "\"");
    System.out.print("Test irc_random_nick_(\"Test\", true) = \"");
    my_int = my_irc.irc_random_nick_("Test", true);
    my_str = my_irc.irc_nick_try;
    System.out.println(my_str + "\"");
    System.out.print("Test is_irc_nick_(\"" + my_str + "\") = ");
    System.out.println(my_irc.is_irc_nick_(my_str));
  };
  
  public static void main(String args[]) {
    irc_testing();
    irc_checks();
  };
}

