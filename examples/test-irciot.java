/*
 * Copyright (c) 2020 Alexey Y. Woronov
 */

import java.lang.*;
import javairciot.*;
import java.nio.charset.Charset;

class test_javairciot extends javairciot.jlayerirciot {

  public static void irc_testing() {
    System.out.print("This is IRC-IoT testing library, ");
    System.out.println("JLayerIRCIoT (protocol) class");
    System.out.print("Library version: ");
    System.out.print(CONST.irciot_library_version);
    System.out.print(", IRC-IoT protocol version: ");
    System.out.println(CONST.irciot_protocol_version);
  };

  public static void irc_checks() {
    jlayerirciot my_irciot = new jlayerirciot();
    String[] my_addresses = new String[] { "", "Hello@World",
      "server@location1/device@location2", "@location1/device",
      "t@t", "wekfj24er24'f,f4ef_ ed'e2@@@@" };
    boolean[] my_results = new boolean[] { true, true,
      true, true, true, false };
    System.out.println("");
    for (int my_idx = 0;my_idx < my_addresses.length;my_idx++) {
      String my_str = my_addresses[my_idx];
      System.out.print("Test is_irciot_address_(\"" + my_str + "\") = ");
      boolean my_bool = my_irciot.is_irciot_address_(my_str);
      String my_out = "FAILED";
      if (my_bool == my_results[my_idx]) my_out = "OK";
      System.out.println(my_bool + " -- " + my_out);

    };

    String my_password = "MY_password-inside-stringxxx@";
    System.out.println("");
    byte[] my_bytes = my_password.getBytes(Charset.forName("UTF-8"));
    my_irciot.irciot_crc16_init_();
    String my_crc16 = my_irciot.irciot_crc16_(my_bytes);
    String my_addon = null;
    Integer my_len = 0;
    if (my_crc16 != null) {
      System.out.print("Test irciot_crc_16_('" + my_password);
      System.out.print("') = 0x" + my_crc16);
      if (my_crc16.equals("5c66")) System.out.println(" -- OK");
      else System.out.println(" -- FAILED");
    } else System.out.println(" <null> -- FAILED");
    String my_crc32 = my_irciot.irciot_crc32_(my_bytes);
    if (my_crc32 != null) {
      System.out.print("Test irciot_crc_32_('" + my_password);
      System.out.print("') = 0x" + my_crc32);
      if (my_crc32.equals("08623ca6")) System.out.println(" -- OK");
      else System.out.println(" -- FAILED");
    } else System.out.println(" <null> -- FAILED");
  };

  public static void main(String args[]) {
    irc_testing();
    irc_checks();
  };

}


