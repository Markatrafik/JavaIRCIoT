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

  public static void irc_ip_test() {
    jlayerirc my_irc = new jlayerirc();
    String my_ip = "127.0.0.1";
    System.out.print("\nTest is_ipv4_address_(\"" + my_ip + "\") = ");
    System.out.println(my_irc.is_ipv4_address_(my_ip));
    my_ip = "235.345.63.1";
    System.out.print("Test is_ipv4_address_(\"" + my_ip + "\") = ");
    System.out.println(my_irc.is_ipv4_address_(my_ip));
    my_ip = "2a00:1450:4010:c06::8b";
    System.out.print("\nTest is_ipv6_address_(\"" + my_ip + "\") = ");
    System.out.println(my_irc.is_ipv6_address_(my_ip));
    my_ip = "2a00:1450:4010:c06::8z";
    System.out.print("Test is_ipv6_address_(\"" + my_ip + "\") = ");
    System.out.println(my_irc.is_ipv6_address_(my_ip));
  };
  
  public static void main(String args[]) {
    irc_testing();
    irc_ip_test();
  };
}

