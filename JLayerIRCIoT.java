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

import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

  public static final class init_CONST {
   //
   public String irciot_library_version = "0.0.165";
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
   //
   public String[] tag_ALL_BASE32_ENC = new String[] {
     tag_ENC_BASE32,
     tag_ENC_B32_ZLIB,
     tag_ENC_B32_BZIP2
   };
   //
   public String[] tag_ALL_BASE64_ENC = new String[] {
     tag_ENC_BASE64,
     tag_ENC_B64_AES,
     tag_ENC_B64Z_AES,
     tag_ENC_B64_ZLIB,
     tag_ENC_B64_BZIP2,
     tag_ENC_B64_RSA,
     tag_ENC_B64Z_RSA,
     tag_ENC_B64_2FISH,
     tag_ENC_B64Z_2FISH
   };
   //
   public String[] tag_ALL_BASE85_ENC = new String[] {
     tag_ENC_BASE85,
     tag_ENC_B85_ZLIB,
     tag_ENC_B85_BZIP2,
     tag_ENC_B85_AES,
     tag_ENC_B85Z_AES,
     tag_ENC_B85_2FISH,
     tag_ENC_B85Z_2FISH
   };
   //
   public String[] tag_ALL_BASE122_ENC = new String[] {
     tag_ENC_BASE122
   };
   //
   public String[] tag_ALL_nocompres_ENC = new String[] {
     tag_ENC_BASE32,
     tag_ENC_BASE64,
     tag_ENC_BASE85,
     tag_ENC_BASE122,
     tag_ENC_B64_RSA,
     tag_ENC_B85_RSA,
     tag_ENC_B64_AES,
     tag_ENC_B85_AES,
     tag_ENC_B64_2FISH,
     tag_ENC_B85_2FISH
   };
   //
   public String[] tag_ALL_ZLIB_ENC = new String[] {
     tag_ENC_B64_ZLIB,
     tag_ENC_B85_ZLIB,
     tag_ENC_B64Z_RSA,
     tag_ENC_B85Z_RSA,
     tag_ENC_B64Z_AES,
     tag_ENC_B85Z_AES,
     tag_ENC_B64Z_2FISH,
     tag_ENC_B85Z_2FISH
   };
   //
   public String[] tag_ALL_BZIP2_ENC = new String[] {
     tag_ENC_B32_BZIP2,
     tag_ENC_B64_BZIP2,
     tag_ENC_B85_BZIP2
   };
   //
   public String[] tag_ALL_RSA_ENC = new String[] {
     tag_ENC_B64_RSA,
     tag_ENC_B85_RSA,
     tag_ENC_B64Z_RSA,
     tag_ENC_B85Z_RSA
  };
  //
  public String[] tag_ALL_AES_ENC = new String[] {
     tag_ENC_B64_AES,
     tag_ENC_B85_AES,
     tag_ENC_B64Z_AES,
     tag_ENC_B85Z_AES
  };
  //
  public String[] tag_ALL_2FISH_ENC = new String[] {
     tag_ENC_B64_2FISH,
     tag_ENC_B85_2FISH,
     tag_ENC_B64Z_2FISH,
     tag_ENC_B85Z_2FISH
  };
  //
  public String[] tag_ALL_nocrypt_ENC = new String[] {
     tag_ENC_BASE32,
     tag_ENC_BASE64,
     tag_ENC_BASE85,
     tag_ENC_BASE122,
     tag_ENC_B32_ZLIB,
     tag_ENC_B64_ZLIB,
     tag_ENC_B85_ZLIB,
     tag_ENC_B32_BZIP2,
     tag_ENC_B64_BZIP2,
     tag_ENC_B85_BZIP2
   };
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
   public int[] crypto_ALL_asymmetric  = new int[] {
    crypto_RSA };
   public int[] crypto_ALL_symmetric   = new int[] {};
   public int[] crypto_ALL_private_key = new int[] {
    crypto_AES, crypto_2FISH };
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
   public HashMap<Integer, String> err_DESCRIPTIONS = new HashMap<Integer, String>() {{
     put( err_PROTO_VER_MISMATCH, "Protocol version mismatch" );
     put( err_LIB_VER_MISMATCH,   "Library version mismatch" );
     put( err_BASE64_DECODING,    "BASE64 decoding" );
     put( err_BASE85_DECODING,    "BASE85 decoding" );
     put( err_BASE32_DECODING,    "BASE32 decoding" );
     put( err_DEFRAG_INVALID_DID, "Invalid 'dp' when defragmenting" );
     put( err_CONTENT_MISSMATCH,  "Content missmatch" );
     put( err_DEFRAG_OP_MISSING,  "No tag 'op' when defragmenting" );
     put( err_DEFRAG_DP_MISSING,  "No tag 'dp' when defragmenting" );
     put( err_DEFRAG_BP_MISSING,  "No tag 'bp' when defragmenting" );
     put( err_DEFRAG_OC_EXCEEDED, "Exceeded 'oc' field value" );
     put( err_DEFRAG_DC_EXCEEDED, "Exceeded 'dc' field value" );
     put( err_DEFRAG_BC_EXCEEDED, "Exceeded 'bc' field value" );
     put( err_OVERLAP_MISSMATCH,  "Overlapping fragments missmatch" );
     put( err_COMP_ZLIB_HEADER,   "Invalid Zlib header" );
     put( err_COMP_ZLIB_INCOMP,   "Zlib incomplete block" );
     put( err_RSA_KEY_FORMAT,     "Invalid RSA Key format" );
     put( err_LOAD_ZLIB_MODULE,   "Loading Zlib module" );
     put( err_LOAD_BZIP2_MODULE,  "Loading BZIP2 module" );
     put( err_LOAD_RSA_MODULE,    "Loading RSA module" );
     put( err_LOAD_AES_MODULE,    "Loading AES module" );
     put( err_LOAD_2FISH_MODULE,  "Loading Twofish module" );
     put( err_LOAD_USER_SIGN,     "Loading UserSign module" );
     put( err_LOAD_USER_CRYPT,    "Loading UserCrypt module" );
     put( err_LDICT_VERIFY_OK,    "Local Dictionary verification OK" );
     put( err_LDICT_VERIFY_FAIL,  "Local Dictionary verification failed" );
   }};
   //
   public byte pattern = 0; // or 64 "@", 255
   //
   // Default Maximum IRC message size (in bytes)
   //
   public int default_mtu = 450; // Undernet IRCd at 2019
   //
   // Fragmented Message Delete Timeout (in seconds)
   //
   public int FMDT = 3600;
   //
   // Message Fragment auto re-Request Time (in seconds)
   //
   public int MFRT = 60;
   //
   // BlockCHain key publication Timeout (in seconds)
   public int BCHT = 86400;
   // ENCryption key publication Timeout (in seconds)
   public int ENCT = 86400;
   //
   public String mod_USERSIGN  = "irciot-usersign";
   public String mod_USERCRYPT = "irciot-usercrypt";
   //
   public int crc16_start = 0xB001;
   //
   public int virtual_mid_pipeline_size = 16;
   //
   public int default_integrity_check = 0;
   //
   // 0 is No Integrity Check
   // 1 is CRC16 Check "c1": +12 bytes
   // 2 is CRC32 Check "c2": +14 bytes
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
  };

  // Global Variables
  //
  public static final init_CONST CONST = new init_CONST();
  //
  public String current_mid = "0"; // Message ID
  public int    current_oid =  0 ; // Object ID
  public int    current_did =  0 ; // Datum ID
  //
  public int mid_lock = 0;
  public int oid_lock = 0;
  public int did_lock = 0;
  //
  public boolean defrag_lock = false;
  //
  public boolean output_lock = false;
  //
  public String  ldict_file  = null;
  public boolean ldict_lock  = false;
  //
  public String mid_method = CONST.tag_mid_default;
  public int    oid_method = 0;
  public int    did_method = 0;
  //
  public String crypt_method = CONST.tag_ENC_default;
  public int crypt_model = this.irciot_crypto_get_model_(this.crypt_method);
  public int crypt_algo = this.irciot_crypto_get_algorithm_(this.crypt_method);
  public int crypt_base = this.irciot_crypto_get_base_(this.crypt_method);
  public int crypt_compress = this.irciot_crypto_get_compress_(this.crypt_method);
  //
  public int blockchain_key_published = 0;
  //
  public int encryption_key_published = 0;
  //
  public int message_mtu = CONST.default_mtu;
  //
  public int integrity_check = CONST.default_integrity_check;
  //
  public JLayerIRCIoT() { // Class constructor
    //

  };

  public String irciot_protocol_version_() {
    return this.CONST.irciot_protocol_version;
  };
  //
  public String irciot_library_version_() {
    return this.CONST.irciot_library_version;
  };
  //
  public Pair<String, String> irciot_compatibility_() {
    return Pair.with(this.CONST.irciot_protocol_version, this.CONST.irciot_library_version);
  };
  //

  //
  public String irciot_crypto_wo_encryption_(String in_crypt_method) {
    int my_base = this.irciot_crypto_get_base_(in_crypt_method);
    int my_compress = this.irciot_crypto_get_compress_(in_crypt_method);
    if (my_base == this.CONST.base_BASE32)
      if (my_compress == this.CONST.compress_ZLIB)
        return this.CONST.tag_ENC_B32_ZLIB;
      else if (my_compress == this.CONST.compress_BZIP2)
        return this.CONST.tag_ENC_B32_BZIP2;
      else return this.CONST.tag_ENC_BASE32;
    else if (my_base == this.CONST.base_BASE64)
      if (my_compress == this.CONST.compress_ZLIB)
        return this.CONST.tag_ENC_B64_ZLIB;
      else if (my_compress == this.CONST.compress_BZIP2)
        return this.CONST.tag_ENC_B64_BZIP2;
      else if (my_base == this.CONST.base_BASE85)
        if (my_compress == this.CONST.compress_ZLIB)
          return this.CONST.tag_ENC_B85_ZLIB;
        else if (my_compress == this.CONST.compress_BZIP2)
          return this.CONST.tag_ENC_B85_BZIP2;
        else return this.CONST.tag_ENC_BASE85;
    return this.CONST.tag_ENC_BASE64;
  };
  // End of irciot_crypto_wo_encryption_()

  public int irciot_crypto_get_model_(String in_crypt_method) {
    if (Arrays.asList(this.CONST.tag_ALL_nocrypt_ENC).contains(in_crypt_method)) {
      return this.CONST.crypt_NO_ENCRYPTION;
    } else {
      int my_algo = this.irciot_crypto_get_algorithm_(in_crypt_method);
      if (Arrays.asList(this.CONST.crypto_ALL_asymmetric).contains(my_algo))
        return this.CONST.crypt_ASYMMETRIC;
      else if (Arrays.asList(this.CONST.crypto_ALL_symmetric).contains(my_algo))
        return this.CONST.crypt_SYMMETRIC;
      else if (Arrays.asList(this.CONST.crypto_ALL_private_key).contains(my_algo))
        return this.CONST.crypt_PRIVATE_KEY;
    };
    return this.crypt_model;
  };
  // End of irciot_crypto_get_model_()

  public int irciot_crypto_get_base_(String in_crypt_method) {
    if (Arrays.asList(this.CONST.tag_ALL_BASE32_ENC).contains(in_crypt_method))
      return this.CONST.base_BASE32; else
    if (Arrays.asList(this.CONST.tag_ALL_BASE64_ENC).contains(in_crypt_method))
      return this.CONST.base_BASE64; else
    if (Arrays.asList(this.CONST.tag_ALL_BASE85_ENC).contains(in_crypt_method))
      return this.CONST.base_BASE85; else
    if (Arrays.asList(this.CONST.tag_ALL_BASE122_ENC).contains(in_crypt_method))
      return this.CONST.base_BASE122;
    return this.crypt_base;
  };
  // End of irciot_crypto_get_base_()

  public int irciot_crypto_get_compress_(String in_crypt_method) {
    if (Arrays.asList(this.CONST.tag_ALL_nocompres_ENC).contains(in_crypt_method))
      return this.CONST.compress_NONE; else
    if (Arrays.asList(this.CONST.tag_ALL_ZLIB_ENC).contains(in_crypt_method))
      return this.CONST.compress_ZLIB; else
    if (Arrays.asList(this.CONST.tag_ALL_BZIP2_ENC).contains(in_crypt_method))
      return this.CONST.compress_BZIP2;
    return this.crypt_compress;
  };
  // End of irciot_crypto_get_compress_()

  public int irciot_crypto_get_algorithm_(String in_crypt_method) {
    if (Arrays.asList(this.CONST.tag_ALL_RSA_ENC).contains(in_crypt_method))
      return this.CONST.crypto_RSA;
    else if (Arrays.asList(this.CONST.tag_ALL_AES_ENC).contains(in_crypt_method))
      return this.CONST.crypto_AES;
    else if (Arrays.asList(this.CONST.tag_ALL_2FISH_ENC).contains(in_crypt_method))
      return this.CONST.crypto_2FISH;
    return this.crypt_algo;

  };
  // End of irciot_crypto_get_algorithm_()

  // incomplete
  public Pair<String, Integer> irciot_encap_bigdatum_(JSONArray in_datums, int in_part) {

    return Pair.with("", 0);
  };

  // incomplete
  public String irciot_encap_internal_(String in_datumset) {
    try {
      JSONParser my_parser = new JSONParser();
      JSONObject my_datums = (JSONObject) my_parser.parse(in_datumset);
    } catch (ParseException e) {
      // e.printStackTrace();
      return "";
    };
    String my_irciot = "";


    return my_irciot;
  };
  // End of irciot_encap_internal_()

  // incomplete
  public List<Pair<String, String>> irciot_encap_all_() {
    //

    List<Pair<String, String>> my_out = new ArrayList<>();

    my_out.add(Pair.with("", ""));
    return my_out;
  };

  // incomplete
  public Triplet<String, Integer, Integer> irciot_encap_(String in_datumset, int in_skip, int in_part) {
    String my_irciot = "";
    String my_datumset = in_datumset;
    boolean my_encrypt = false;
    int my_datums_skip = 0;
    int my_datums_part = 0;
    int my_datums_cnt  = 0;
    String save_mid = this.current_mid;
    Object my_json_obj = (Object) null;
    JSONArray my_datums = (JSONArray) null;
    JSONParser my_parser = new JSONParser();
    try {
      my_json_obj = (Object) my_parser.parse(my_datumset);
    } catch (ParseException e) {
      // e.printStackTrace();
      return Triplet.with("", 0, 0);
    };
    int my_total = 0;
    if (my_json_obj instanceof JSONArray) {
      my_datums = (JSONArray) my_json_obj;
      my_total = my_datums.size();
    } else return Triplet.with("", 0, 0);
    if (in_skip > 0) {
      my_datums_cnt = 0;

    };
    my_irciot = this.irciot_encap_internal_(my_datumset);
    if ((my_irciot.length() > this.message_mtu) || my_encrypt) {
      if (in_skip == 0)
        this.current_mid = save_mid; // mid rollback
      try {
        my_json_obj = (Object) my_parser.parse(my_datumset);
      } catch (ParseException e) {
        // e.printStackTrace();
        return Triplet.with("", 0, 0);
      };
      int one_datum = 0;
      if (my_json_obj instanceof JSONArray) {
        my_datums = (JSONArray) my_json_obj;
        int my_datums_total = my_datums.size();
        if (my_datums_total > 1) {
          my_datums_skip = my_datums_total;
          while ((my_irciot.length() > this.message_mtu) &&
            (my_datums_skip <= my_datums_total)) {
            JSONArray part_datums = (JSONArray) null;
            my_datums_cnt = 0;
            // String my_datum = "";
            // Iterator<String> my_iterator = my_datums.iterator();
            // while (my_iterator.hasNext()) {
            //   if (my_datums_cnt < my_datums_skip)
            //     my_datum = my_iterator.next();
            //     part_datums.add(my_datum);
            // };
            if (part_datums.size() == 0) break;
            String str_part_datums = part_datums.toJSONString();
            this.current_mid = save_mid; // mid rollback
            my_irciot = this.irciot_encap_internal_(str_part_datums);
            if (my_irciot.length() <= this.message_mtu) {
              int my_skip_out = in_skip + my_datums_skip;
              if (my_skip_out >= my_total)
                my_skip_out = 0;
              return Triplet.with(my_irciot, my_skip_out, 0);
            };
            my_datums_skip -= 1;
          }; // while
          one_datum = 1; // Multidatum, but current "Datum" is too large
        } else
          one_datum = 1; // One "Datum" in list, and it is too large
      };
      if (my_json_obj instanceof JSONObject)
        one_datum = 1; // One large "Datum" without list
      if (my_encrypt)
        one_datum = 1;
      if (one_datum == 1) {
        this.current_mid = save_mid; // Message ID rollback
        Pair<String, Integer> my_pair
          = this.irciot_encap_bigdatum_(my_datums, in_part);
        my_irciot = my_pair.getValue0();
        my_datums_part = my_pair.getValue1();
        if (my_datums_part == 0)
          my_datums_skip += 1;
      };
    } else {
      my_datums_skip = my_total - in_skip;

    };
    if (my_datums_part == 0)
      this.current_oid += 1;
    if (in_skip + my_datums_skip >= my_total) {
      in_skip = 0;
      my_datums_skip = 0;
      if (this.CAN_encrypt_datum && my_datums_part == 0) {
        // this.crypt_cache = null;
      };
    };
    return Triplet.with(my_irciot, in_skip + my_datums_skip, my_datums_part);
  };
  // End of irciot_encap_()

}
