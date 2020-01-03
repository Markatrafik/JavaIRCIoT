/*
 * JavaIRCIoT (JLayerIRCIoT class)
 *
 * Copyright (c) 2020 Alexey Y. Woronov
 *
 * By using this file, you agree to the terms and conditions set
 * forth in the LICENSE file which can be found at the top level
 * of this package
 *
 * Authors:
 *   Alexey Y. Woronov <alexey@woronov.ru>
 */

// Those Global options override default behavior and memory usage:
//

class JLayerIRCIoT {

  public static final boolean CAN_debug_library  = false;
  // Creating a chain of cryptographic signatures:
  public static final boolean CAN_mid_blockchain = false;
  // Ability to encrypt and decrypt of "Datums":
  public static final boolean CAN_encrypt_datum  = false;
  // Ability to compress and decompress "Datums":
  public static final boolean CAN_compress_datum = true;
  //
  // Always encrypt "Datums" in IRC-IoT messages:
  public static final boolean DO_always_encrypt  = false;
  // Automatic loading of necessary modules:
  public static final boolean DO_auto_encryption = false;
  // Automatic loading of necessary modules:
  public static final boolean DO_auto_blockchain = false;
  public static final boolean DO_auto_compress   = false;

  private static class CONST {
   //
   public String irciot_library_version = "0.0.160";
   //
   public String irciot_protocol_version = "0.3.29";
   //
   

  }

  public static CONST CONST = new CONST();

}
