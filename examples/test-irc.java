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
  
  public static void main(String args[]) {
    irc_testing();

  };
}

