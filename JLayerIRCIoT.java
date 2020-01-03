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
 
package JavaIRCIoT;

public class JLayerIRCIoT {

  // Those Global options override default behavior and memory usage:
  //
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

  public static class init_CONST {
   //
   public String irciot_library_version = "0.0.160";
   //
   public String irciot_protocol_version = "0.3.29";
   //
   // IRC-IoT TAGs
   //
   public String tag_MESSAGE_ID  = "mid"; // Message ID
   public String tag_MESSAGE_OC  = "oc";  // Objects Count
   public String tag_MESSAGE_OP  = "op";  // Objects Passed
   public String tag_OBJECT      = "o";   // Object Tag
   public String tag_OBJECT_ID   = "oid"; // Object ID
   public String tag_OBJECT_TYPE = "ot";  // Object Type
   public String tag_OBJECT_DC   = "dc";  // Datums Count
   public String tag_OBJECT_DP   = "dp";  // Datums Passed
   public String tag_DATUM       = "d";   // Datum Tag
   public String tag_DATUM_ID    = "did"; // Datum ID
   public String tag_DATUM_BC    = "bc";  // Bytes Count
   public String tag_DATUM_BP    = "bp";  // Bytes Passed
   public String tag_DATE_TIME   = "t";   // Date and Time
   public String tag_SRC_ADDR    = "src"; // Source Address
   public String tag_DST_ADDR    = "dst"; // Destination Address
   public String tag_VERSION     = "ver"; // IRC-IoT Protocol Version
   public String tag_RETRY_LOST  = "lst"; // Request to resend LoST data
   public String tag_LIVES_COUNT = "ttl"; // Time-To-Live counter
   public String tag_ENC_DATUM   = "ed";  // Encrypted Datum
   //

  }

  public static init_CONST CONST = new init_CONST();

}
