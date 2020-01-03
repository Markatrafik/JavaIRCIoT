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
   public String irciot_library_version = "0.0.161";
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
   // Special TAGs, not reserved for "Object" level
   // Will be replaced by "Dictionaries" mechanism:
   //
   public String tag_ERROR_CODE  = "ec";  // Error code
   public String tag_DESCRIPTION = "dsc"; // Description (e.g. error)
   public String tag_ENC_METHOD  = "em";  // Encryption Method
   public String tag_ENC_PUBKEY  = "ek";  // Encryption Public Key
   public String tag_BCH_METHOD  = "bm";  // Blockchain Method
   public String tag_BCH_PUBKEY  = "bk";  // Blockchain Public Key
   //
   // Container Integrity Checks:
   //
   public String tag_CHK_CRC16   = "c1";
   public String tag_CHK_CRC32   = "c2";
   //
   // Only Basecoding methods:
   //
   public String tag_ENC_BASE32   = "b32p";
   public String tag_ENC_BASE64   = "b64p";
   public String tag_ENC_BASE85   = "b85p";
   public String tag_ENC_BASE122  = "b122";
   //
   // Basecoding + Compression:
   //
   public String tag_ENC_B32_ZLIB  = "b32z";
   public String tag_ENC_B64_ZLIB  = "b64z";
   public String tag_ENC_B85_ZLIB  = "b85z";
   public String tag_ENC_B32_BZIP2 = "b32b";
   public String tag_ENC_B64_BZIP2 = "b64b";
   public String tag_ENC_B85_BZIP2 = "b85b";
   //
   // Basecoding + Encryption:
   //
   public String tag_ENC_B64_RSA   = "b64r";
   public String tag_ENC_B85_RSA   = "b85r";
   public String tag_ENC_B64_2FISH = "b64f";
   public String tag_ENC_B85_2FISH = "b85f";
   public String tag_ENC_B64_AES   = "b64a";
   public String tag_ENC_B85_AES   = "b85a";
   public String tag_ENC_B64_USER  = "b64u";
   public String tag_ENC_B85_USER  = "b85u";
   //
   // Basecoding + Encryption + Compression:
   //
   public String tag_ENC_B64Z_RSA   = "b64Z";
   public String tag_ENC_B85Z_RSA   = "b85Z";
   public String tag_ENC_B64Z_AES   = "b64A";
   public String tag_ENC_B85Z_AES   = "b85A";
   public String tag_ENC_B64Z_2FISH = "b64F";
   public String tag_ENC_B85Z_2FISH = "b85F";
   public String tag_ENC_B64Z_USER  = "b64U";
   public String tag_ENC_B85Z_USER  = "b85U";
   //
   // Blockchain signing methods:
   //
   public String tag_mid_ED25519   = "ed"; // RFC 8032
   public String tag_mid_RSA1024   = "rA";
   public String tag_mid_GOST12    = "gT"; // RFC 7091 (GOST 34.10-2012)
   // ID of signing-algorithm that was added by User:
   public String tag_mid_USERSIGN  = "us";
   //
   public int mid_ED25519_hash_length = 88;
   public int mid_RSA1024_hash_length = 173;
   //
   public String tag_ENC_default = tag_ENC_BASE64;
   //
   public String tag_mid_default = JLayerIRCIoT.CAN_mid_blockchain ? tag_mid_ED25519 : "";
   // Simple IRC-IoT blockchain signing by ED25519
   //
   public int crypt_NO_ENCRYPTION = 0;
   public int crypt_PRIVATE_KEY   = 1;
   public int crypt_SYMMETRIC     = 3;
   public int crypt_ASYMMETRIC    = 5;
   //
   public int crypto_RSA_KEY_SIZE = 2048; // RSA2048
   public int crypto_RSA_SHA_SIZE = 160;  // SHA-1
   public int crypto_RSA_CHUNK    = crypto_RSA_KEY_SIZE % 8;
   public int crypto_RSA_OVERHEAD = (crypto_RSA_SHA_SIZE % 4) + 2;
   public int crypto_RSA_CHUNK_IN = crypto_RSA_CHUNK - crypto_RSA_OVERHEAD;
   //
   // The correlation table between
   // SHA Cipher and RSA algorithm key size
   //
   // Cipher |SIZE|OVER|1024|2048|3072|4096
   // SHA-1  | 160|  42|  86| 214| 342| 470
   // SHA-224| 224|  58|  70| 198| 326| 454
   // SHA-256| 256|  66|  62| 190| 318| 446
   // SHA-384| 384|  98|  30| 158| 286| 414
   // SHA-512| 512| 130| N/A| 126| 254| 382
   //
   public int crypto_RSA   = 1;
   public int crypto_AES   = 5;
   public int crypto_2FISH = 7;
   //
   public int base_BASE32  = 32;
   public int base_BASE64  = 64;
   public int base_BASE85  = 85;
   public int base_BASE122 = 122;
   //
   public int compress_NONE  = 0;
   public int compress_ZLIB  = 3;
   public int compress_BZIP2 = 102;
   //
   // The Object Types, will be replaced
   // by IRC-IoT "Dictionaries" mechanism
   //
   public String ot_BCH_INFO    = "bchnfo"; // Blockchain Information
   public String ot_BCH_REQUEST = "bchreq"; // Blockchain Request
   public String ot_BCH_ACK     = "bchack"; // Blockchain Acknowledgment
   //
   public String ot_ENC_INFO    = "encnfo"; // Encryption Information
   public String ot_ENC_REQUEST = "encreq"; // Encryption Request
   public String ot_ENC_ACK     = "encack"; // Encryption Acknowledgment
   //
   public String ot_ERROR       = "err";    // General errors
   //
   public int api_GET_LMID = 101; // Get Last Message ID
   public int api_SET_LMID = 102; // Set Last Message ID
   public int api_GET_OMID = 111; // Get Own last Message ID
   public int api_SET_OMID = 112; // Set Own last Message ID
   public int api_GET_EKEY = 301; // Get Encryption Key
   public int api_SET_EKEY = 302; // Set Encryption Key
   public int api_GET_EKTO = 351; // Get Encryption Key Timeout
   public int api_SET_EKTO = 352; // Set Encryption Key Timeout
   public int api_GET_BKEY = 501; // Get Blockchain Key
   public int api_SET_BKEY = 502; // Set Blockchain Key
   public int api_GET_BKTO = 551; // Get Blockchain Key Timeout
   public int api_SET_BKTO = 552; // Set Blockchain Key Timeout
   public int api_GET_VUID = 700; // Get list of Virtual User IDs
   //
   public char api_vuid_cfg = 'c'; // VUID prefix for users from config
   public char api_vuid_tmp = 't'; // VUID prefix for temporal users
   public char api_vuid_srv = 's'; // VUID prefix for IRC-IoT Services
   public char api_vuid_all = '*'; // Means All users VUIDs when sending messages
   //
   public String api_vuid_self = "c0"; // Default preconfigured VUID
   //
   // Basic IRC-IoT Services
   //
   public String api_vuid_CRS  = "sC"; // Сryptographic Repository Service
   public String api_vuid_GDS  = "sD"; // Global Dictionary Service
   public String api_vuid_GRS  = "sR"; // Global Resolving Service
   public String api_vuid_GTS  = "sT"; // Global Time Service
   //
   public String api_vuid_RoS  = "sr"; // Primary Routing Service
   //
   public int type_UNDEFINED =  0;
   public int type_NUMERIC   = 10;
   public int type_FLOAT     = 11;
   public int type_STRING    = 12;
   public int type_TEXT      = 13;
   public int type_OBJECT    = 14; // Link to other objects
   public int type_BINARY    = 15; // Binary data block
   public int type_ARRAY     = 16;
   //
   public byte b_LITTLE_ENDIAN = 0;
   public byte b_BIG_ENDIAN    = 1;
   public byte b_MIDDLE_ENDIAN = 2;
   //
   public String ldict_VERSION = "irciot_protocol";
   //
   public String ldict_ITEMS_TABLE  = "items";
   public String ldict_TYPES_TABLE  = "types";
   public String ldict_SECTS_TABLE  = "sections";
   //
   public String ldict_ITEM_ID      = "item_id";
   public String ldict_ITEM_OT      = "item_ot";
   public String ldict_ITEM_NAME    = "item_name";
   public String ldict_ITEM_PARENT  = "parent_item_id";
   public String ldict_ITEM_TYPEID  = "type_id";
   public String ldict_ITEM_TYPEPR  = "type_parameters";
   public String ldict_ITEM_DEFVAL  = "default_value";
   public String ldict_ITEM_CHILD   = "child_object_id";
   public String ldict_ITEM_METHOD  = "method";
   public String ldict_ITEM_LANG    = "method_language";
   public String ldict_ITEM_SECTS   = "sections";
   //
   public String ldict_TYPE_ID      = "type_id";
   public String ldict_TYPE_NAME    = "type_name";
   public String ldict_TYPE_TYPE    = "type_of_type";
   public String ldict_TYPE_ARR     = "is_it_array";
   public String ldict_TYPE_DYNSIZE = "is_variable_dynamically_sized";
   public String ldict_TYPE_DYNARR  = "is_array_dynamically_sized";
   public String ldict_TYPE_ARRSIZE = "size_of_array";
   public String ldict_TYPE_SIZE    = "size";
   public String ldict_TYPE_MIN     = "interval_minimum";
   public String ldict_TYPE_MAX     = "interval_maximum";
   public String ldict_TYPE_PRECIS  = "precision";
   public String ldict_TYPE_EXPSIZE = "exponent";
   public String ldict_TYPE_ENDIAN  = "endianness";
   public String ldict_TYPE_ENCODE  = "encoding";
   //
   public String ldict_SECT_ID      = "section_id";
   public String ldict_SECT_ITEMS   = "items_ids";
   public String ldict_SECT_CHECKS  = "checking_values";
   public String ldict_SECT_METHOD  = "method";
   public String ldict_SECT_LANG    = "method_language";
   //
   // IRC-IoT Errors
   //
   public int err_PROTO_VER_MISMATCH  = 101;
   public int err_LIB_VER_MISMATCH    = 102;
   public int err_DEFRAG_INVALID_DID  = 103;
   public int err_CONTENT_MISSMATCH   = 104;
   public int err_DEFRAG_OP_MISSING   = 111;
   public int err_DEFRAG_DP_MISSING   = 112;
   public int err_DEFRAG_BP_MISSING   = 113;
   public int err_DEFRAG_OC_EXCEEDED  = 121;
   public int err_DEFRAG_DC_EXCEEDED  = 122;
   public int err_DEFRAG_BC_EXCEEDED  = 123;
   public int err_OVERLAP_MISSMATCH   = 131;
   public int err_BASE64_DECODING     = 251;
   public int err_BASE32_DECODING     = 252;
   public int err_BASE85_DECODING     = 253;
   public int err_COMP_ZLIB_HEADER    = 301;
   public int err_COMP_ZLIB_INCOMP    = 303;
   public int err_RSA_KEY_FORMAT      = 351;
   public int err_LDICT_VERIFY_OK     = 811;
   public int err_LDICT_VERIFY_FAIL   = 812;
   //
   public int err_LOAD_ZLIB_MODULE    = 701;
   public int err_LOAD_BZIP2_MODULE   = 702;
   public int err_LOAD_RSA_MODULE     = 731;
   public int err_LOAD_AES_MODULE     = 732;
   public int err_LOAD_2FISH_MODULE   = 733;
   public int err_LOAD_USER_SIGN      = 755;
   public int err_LOAD_USER_CRYPT     = 777;
   //

   //
   public init_CONST() {
     if (JLayerIRCIoT.CAN_compress_datum) {
       if (JLayerIRCIoT.CAN_encrypt_datum) {
         this.tag_ENC_default = tag_ENC_B64Z_RSA;
       } else {
         this.tag_ENC_default = tag_ENC_B64_ZLIB;
       };
     } else {
       if (JLayerIRCIoT.CAN_encrypt_datum) {
         this.tag_ENC_default = tag_ENC_B64_RSA;
       } else {
         this.tag_ENC_default = tag_ENC_BASE64;
       };
     };
   };

  }

  public static init_CONST CONST = new init_CONST();

}
