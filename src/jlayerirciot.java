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
 
package javairciot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.digest.DigestUtils;
import org.javatuples.Pair;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.javatuples.Ennead;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;
@SuppressWarnings("unchecked")

public class jlayerirciot {

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

  public static final class init_constants {
   //
   private static final long serialVersionUID = 32767;
   //
   public String irciot_library_version = "0.0.191";
   //
   public String irciot_protocol_version = "0.3.33";
   //
   // IRC-IoT characters:
   //
   public String irciot_chars_lower = "abcdefghijklmnopqrstuvwxyz";
   public String irciot_chars_upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   public String irciot_chars_digit = "0123456789";
   public String irciot_chars_addon = "-_.";
   public String irciot_chars_addr_cell = irciot_chars_lower
     + irciot_chars_upper + irciot_chars_digit + irciot_chars_addon;
   //
   public String irciot_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
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
   public String tag_mid_default = jlayerirciot.CAN_mid_blockchain ? tag_mid_ED25519 : "";
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
   // IRC-IoT Base Types
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
   public int err_DEFRAG_MISSMATCH    = 133;
   public int err_BASE64_DECODING     = 251;
   public int err_BASE32_DECODING     = 252;
   public int err_BASE85_DECODING     = 253;
   public int err_COMP_ZLIB_HEADER    = 301;
   public int err_COMP_ZLIB_INCOMP    = 303;
   public int err_RSA_KEY_FORMAT      = 351;
   public int err_INVALID_MESSAGE     = 501;
   public int err_INVALID_ADDRESS     = 503;
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
   public HashMap<Integer, String> err_DESCRIPTIONS = new HashMap<Integer, String>() {
     private static final long serialVersionUID = 16383; {
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
     put( err_DEFRAG_MISSMATCH,   "Defragmentation content missmatch" );
     put( err_COMP_ZLIB_HEADER,   "Invalid Zlib header" );
     put( err_COMP_ZLIB_INCOMP,   "Zlib incomplete block" );
     put( err_INVALID_MESSAGE,    "Invalid IRC-IoT message format" );
     put( err_INVALID_ADDRESS,    "Invalid IRC-IoT address format" );
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
   public int crc16_poly = 0xA001;
   // public boolean crc16_reversed = false;
   // 0xA001 -- x^16 + x^15 + x^2 + 1 (CRC-16-IBM)
   // 0xC002 -- x^16 + x^14 + x   + 1 (CRC-16-IBM reversed)
   // 0x8408 -- x^16 + x^12 + x^5 + 1 (CRC-16-CCITT) SDLC/HDLC
   // 0x8810 -- x^16 + x^11 + x^4 + 1 (CRC-16-CCITT reversed)
   // 0x8000 -- x^16 + 1 (LRCC-16)
   // 0x8005
   //
   public int virtual_mid_pipeline_size = 16;
   //
   public int default_integrity_check = 0;
   public int default_integrity_stamp = 0;
   //
   // 0 is No Integrity Check
   // 1 is CRC16 Check "c1": +12 bytes
   // 2 is CRC32 Check "c2": +16 bytes
   //
   public init_constants() {
     if (jlayerirciot.CAN_compress_datum) {
       if (jlayerirciot.CAN_encrypt_datum) {
         this.tag_ENC_default = tag_ENC_B64Z_RSA;
       } else {
         this.tag_ENC_default = tag_ENC_B64_ZLIB;
       };
     } else {
       if (jlayerirciot.CAN_encrypt_datum) {
         this.tag_ENC_default = tag_ENC_B64_RSA;
       } else {
         this.tag_ENC_default = tag_ENC_BASE64;
       };
     };
   };
  };

  // Global Variables
  //
  private static final long serialVersionUID = 65535;
  //
  public static final init_constants CONST = new init_constants();
  //
  public String current_mid = "0"; // Message ID
  public int    current_oid =  0 ; // Object ID
  public int    current_did =  0 ; // Datum ID
  //
  public int mid_lock = 0;
  public int oid_lock = 0;
  public int did_lock = 0;
  //
  public List<Triplet<String, Object, String>> defrag_pool;
  public boolean defrag_lock = false;
  //
  public List<Pair<String, String>> output_pool;
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
  public int crypt_model = this.irciot_crypto_get_model_(crypt_method);
  public int crypt_algo = this.irciot_crypto_get_algorithm_(crypt_method);
  public int crypt_base = this.irciot_crypto_get_base_(crypt_method);
  public int crypt_compress = this.irciot_crypto_get_compress_(crypt_method);
  //
  public Object crypt_RSA  = (Object) null;
  public Object crypt_AES  = (Object) null;
  public Object crypt_FISH = (Object) null;
  // Compression modules:
  public Object crypt_ZLIB = (Object) null;
  public Object crypt_BZ2  = (Object) null;
  //
  public int blockchain_key_published = 0;
  //
  public int encryption_key_published = 0;
  //
  public int message_mtu = CONST.default_mtu;
  //
  public int integrity_check = CONST.default_integrity_check;
  //
  public short[] crc16_table;
  //
  public Random random;
  //
  public jlayerirciot() { // Class constructor
    this.random = new Random();

  };

  public String irciot_protocol_version_() {
    return CONST.irciot_protocol_version;
  };
  //
  public String irciot_library_version_() {
    return CONST.irciot_library_version;
  };
  //
  public Pair<String, String> irciot_compatibility_() {
    return Pair.with(CONST.irciot_protocol_version, CONST.irciot_library_version);
  };

  // incomplete
  public void irciot_error_(int in_error_code, String in_mid,
   String in_vuid, String in_addon) {
    // Warning: This version of error handler is made for
    // testing and does not comply with the specification
    String my_message = "";
    //


    //
  };
  //

  public void irciot_crc16_init_() {
    int my_poly = CONST.crc16_poly;
    short[] my_table = new short[256];
    int my_item = 0;
    for (int my_idx1 = 0;my_idx1 < 256;my_idx1++) {
      my_item = (short) (my_idx1 & 0xFFFF);
      for (int my_idx2 = 0;my_idx2 < 8;my_idx2++) {
        int my_tmp = my_item >> 1;
        if ((my_item & 0x0001) != 0) {
          my_item = my_tmp ^ (my_poly & 0xFFFF);
        } else
          my_item = my_tmp;
      };
      my_table[my_idx1] = (short) (my_item & 0xFFFF);
    };
    this.crc16_table = my_table;
  };

  public String irciot_get_current_datetime_() {
    Calendar my_cal = Calendar.getInstance();
    SimpleDateFormat my_sdf
      = new SimpleDateFormat(CONST.irciot_DATE_FORMAT);
    return my_sdf.format(my_cal.getTime());
  };

  public void irciot_crc32_init_() {
  };

  public String irciot_crc16_(byte[] in_bytes) {
    if (this.crc16_table == null) this.irciot_crc16_init_();
    int my_crc = 0x0000;
    for (byte my_chr : in_bytes)
      my_crc = ((my_crc >> 8) ^ (this.crc16_table[((my_crc ^ my_chr) & 0xFF)]) & 0xFFFF);
    return String.format("%04x", my_crc & 0xFFFF);
  };

  public String irciot_crc32_(byte[] in_bytes) {
    Checksum my_crc = new CRC32();
    my_crc.update(in_bytes, 0, in_bytes.length);
    return String.format("%08x", my_crc.getValue());
  };

  public void irciot_clear_defrag_chain_(Integer in_did) {
    if (in_did == null) return;
    if (this.defrag_lock) return;
    this.defrag_lock = true;
    try {
      Iterator<Triplet<String, Object, String>> my_iterator
        = this.defrag_pool.iterator();
      while (my_iterator.hasNext()) {
        Triplet<String, Object, String> my_item = my_iterator.next();
        Ennead<String, String, String, String,
         Integer, Integer, Integer, Integer, Integer> my_header
          = (Ennead<String, String, String, String,
         Integer, Integer, Integer, Integer, Integer>) my_item.getValue1();
        int my_did = my_header.getValue8();
        if (my_did == in_did) {
          this.defrag_pool.remove(my_item);
          break;
        };
      };
    } catch (Exception my_ex) {};
    this.defrag_lock = false;
  };
  // End of irciot_clear_defrag_chain_()

  public String irciot_base64_decode_(String in_string) {
    byte[] my_arr = Base64.decodeBase64(in_string.getBytes());
    String my_str = new String(my_arr);
    return my_str;
  };

  // incomplete
  public String irciot_base85_decode_(String in_string) {

    return "";
  };

  public String irciot_base32_decode_(String in_string) {
    Base32 base32 = new Base32();
    byte[] my_arr = base32.decode(in_string.getBytes());
    String my_str = new String(my_arr);
    return my_str;
  };

  public String irciot_add_padding_(String in_buffer, Integer in_padding) {
   int my_count = in_padding - (in_buffer.length() % in_padding);
   if (my_count > 0)
     for (int my_idx = 0;my_idx < my_count;my_idx++) in_buffer += '=';
   return in_buffer;
  };

  // incomplete
  public String irciot_defragmentation_(String in_enc,
    Ennead<String, String, String, String, Integer, Integer, Integer, Integer, Integer> in_header,
    String orig_json, String in_vuid) {
    String my_dt = in_header.getValue0();
    String my_ot = in_header.getValue1();
    String my_src = in_header.getValue2();
    String my_dst = in_header.getValue3();
    int my_dc = in_header.getValue4();
    int my_dp = in_header.getValue5();
    int my_bc = in_header.getValue6();
    int my_bp = in_header.getValue7();
    int my_did = in_header.getValue8();
    boolean my_dup = false;
    boolean my_new = false;
    int my_err = 0;
    int my_ok  = 0;
    boolean my_fragments = false;
    String defrag_buffer = "";
    List<Pair<Integer, String>> defrag_array = new ArrayList<>();
    Iterator<Triplet<String, Object, String>> my_iterator
      = this.defrag_pool.iterator();
    while (my_iterator.hasNext()) {
      Triplet<String, Object, String> my_item = my_iterator.next();
      String test_enc = my_item.getValue0();
      String test_json = my_item.getValue2();
      Ennead<String, String, String, String,
       Integer, Integer, Integer, Integer, Integer> my_header
        = (Ennead<String, String, String, String,
       Integer, Integer, Integer, Integer, Integer>) my_item.getValue1();
      String test_dt  = my_header.getValue0();
      String test_ot  = my_header.getValue1();
      String test_src = my_header.getValue2();
      String test_dst = my_header.getValue3();
      Integer test_dc  = my_header.getValue4();
      Integer test_dp  = my_header.getValue5();
      Integer test_bc  = my_header.getValue6();
      Integer test_bp  = my_header.getValue7();
      Integer test_did = my_header.getValue8();
      if (orig_json.equals(test_json)) {
        my_dup = true;
        break;
      } else {
        if (test_did == my_did) {
          my_fragments = true;
          if (my_ot.equals(test_ot) &&
              my_src.equals(test_src) &&
              my_dst.equals(test_dst)) {
            if ((test_dc == my_dc)
             && (test_dp == my_dp)
             && (test_bp == my_bp)
             && (test_bc == my_bc)) {
                  boolean my_enc_ok = false;
                  if (in_enc != null)
                    if (in_enc.equals(test_enc)) {
                      my_dup = true;
                      my_enc_ok = true;
                    };
                  if (!my_enc_ok) {
                    my_err = CONST.err_DEFRAG_MISSMATCH;
                    break;
                  };
            } else {
              if ((test_dc != -1)
               && (test_dp != -1)
               && (test_bc == -1)
               && (test_bp == -1)) {
                if (my_dp == -1) {
                  my_err = CONST.err_DEFRAG_DP_MISSING;
                  break;
                };
                Pair<Integer, String> defrag_item;
                if (defrag_array.size() == 0)
                  defrag_array.add(Pair.with(my_dp, in_enc));
                defrag_array.add(Pair.with(test_dp, test_enc));
                if (defrag_array.size() == my_dc) {
                  my_ok = 1;
                  break;
                } else if (defrag_array.size() > my_dc) {
                  my_err = CONST.err_DEFRAG_DC_EXCEEDED;
                  break;
                };
              } else
                 if ((test_bc != -1)
                  && (test_bp != -1)
                  && (test_dc != -1)
                  && (test_dp != -1)) {
                   if (my_bp == -1) {
                     my_err = CONST.err_DEFRAG_BP_MISSING;
                     break;
                   };
                   if (defrag_buffer.isEmpty()) {
                     for (int my_idx = 0;my_idx < my_bc;my_idx++)
                       defrag_buffer += CONST.pattern;
                     //

                   };
                   if (!defrag_buffer.isEmpty()) {
                     // here will be a comparison of overlapping buffer intervals
                     //

                     int my_count = 0;
                     for (int my_idx = 0;my_idx < my_bc;my_idx++)
                       if (defrag_buffer.charAt(my_idx) == CONST.pattern) my_count++;
                     if (my_count == 0) {
                       my_ok = 2;
                     } else my_new = true;
                   };
                 } else {
                   // Combo fragmentation method
                 };
            };
          } else {
            my_err = CONST.err_DEFRAG_INVALID_DID;
            break;
          };
        };
      };
    };
    if (this.defrag_pool.size() == 0) {
      if (in_enc.length() == my_dc) {
        defrag_buffer = in_enc;
        my_ok = 2;
      } else my_new = true;
    } else if (!my_fragments) my_new = true;
    if (my_err > 0) {
      this.irciot_error_(my_err, null, null, null);
      this.irciot_clear_defrag_chain_(my_did);
      return "";
    };
    if (my_new) {
      this.defrag_lock = true;
      this.defrag_pool.add(Triplet.with(in_enc, in_header, orig_json));
      this.defrag_lock = false;
    };
    if (my_ok > 0) {
      if (my_ok == 1) {
      } else if (my_ok == 2) {
        this.irciot_clear_defrag_chain_(my_did);
        if (CAN_debug_library)
          System.out.println("\033[1;42m DEFRAGMENTATION COMPLETED \033[0m");
        String my_crypt_method = this.crypt_method;
        if (CONST.ot_ENC_INFO.equals(my_ot)
         || CONST.ot_ENC_ACK.equals(my_ot)
         || CONST.ot_BCH_INFO.equals(my_ot)
         || CONST.ot_BCH_ACK.equals(my_ot)
         || CONST.ot_ENC_REQUEST.equals(my_ot)
         || CONST.ot_BCH_REQUEST.equals(my_ot))
          my_crypt_method = this.irciot_crypto_wo_encryption_(this.crypt_method);
        int my_base = this.irciot_crypto_get_base_(my_crypt_method);
        String out_base = new String("");
        if (my_base == CONST.base_BASE64) {
          try {
            defrag_buffer = this.irciot_add_padding_(defrag_buffer, 4);
            out_base = this.irciot_base64_decode_(defrag_buffer);
          } catch (Exception my_ex) {
            this.irciot_error_(CONST.err_BASE64_DECODING, null, null, null);
            return "";
          };
        } else if (my_base == CONST.base_BASE85) {
          try {
            out_base = this.irciot_base85_decode_(defrag_buffer);
          } catch (Exception my_ex) {
            this.irciot_error_(CONST.err_BASE64_DECODING, null, null, null);
            return "";
          };
        } else if (my_base == CONST.base_BASE32) {
          try {
            defrag_buffer = this.irciot_add_padding_(defrag_buffer, 8);
            out_base = this.irciot_base32_decode_(defrag_buffer);
          } catch (Exception my_ex) {
            this.irciot_error_(CONST.err_BASE32_DECODING, null, null, null);
            return "";
          };
        } else {
          //

        };
        int my_algo = this.irciot_crypto_get_algorithm_(my_crypt_method);
        if (my_algo == CONST.crypto_RSA) {

        } else if (my_algo == CONST.crypto_AES) {

        } else if (my_algo == CONST.crypto_2FISH) {

        };
        int my_compress = this.irciot_crypto_get_compress_(my_crypt_method);
        if (my_compress == CONST.compress_NONE) {

        } else if (my_compress == CONST.compress_ZLIB) {

        } else if (my_compress == CONST.compress_BZIP2) {

        } else return "";
        try {
          // Adding missing fields to the "Datum" from parent object

          //
        } catch (Exception my_ex) {
          return "";
        };
      } else return "";
      this.irciot_clear_defrag_chain_(my_did);
      return "";
    };
    if (my_dup) return "";
    return "";
  };
  // End of irciot_defragmentation_()

  // incomplete
  boolean irciot_load_blockchain_methods_(String in_mid_method) {

    return false;
  };
  // End of irciot_load_blockchain_methods_()

  // incomplete
  boolean irciot_load_encryption_methods_(String in_crypt_method) {

    return false;
  };
  // End of irciot_load_encryption_methods_()

  // incomplete
  boolean irciot_load_compression_methods_(String in_compress_method) {

    return false;
  };
  // End of irciot_load_compression_methods_()

  public void irciot_init_blockchain_method_(String in_mid_method) {

  };
  // End of irciot_init_blocchain_method_()

  // incomplete
  public void irciot_enable_blockchain_(String in_mid_method) {
    if (!this.irciot_load_blockchain_methods_(in_mid_method))
      this.irciot_init_blockchain_method_(in_mid_method);
    this.mid_method = in_mid_method;
    this.blockchain_key_published = 0;
  };

  // incomplete
  public void irciot_enable_encryption_(String in_crypt_method) {

  };

  // incomplete
  public void irciot_disable_blockchain_() {
  };

  // incomplete
  public void irciot_disable_encryption_() {
  };

  public String irciot_crypto_security_hasher_(byte[] in_password, String in_algo) {
    try {
      MessageDigest my_md = MessageDigest.getInstance(in_algo);
      BigInteger my_bi = new BigInteger(1, in_password);
      String my_HASH = my_bi.toString(16);
      while (my_HASH.length() < 32) my_HASH = "0" + my_HASH;
      return my_HASH;
    } catch (NoSuchAlgorithmException my_ex) {
      return "";
    } catch (Exception my_ex) {
      return "";
    }
  };

  // incomplete
  public String irciot_crypto_hasher_(byte[] in_password, int in_hash_size) {
    boolean gen_RND = false;
    if (in_password == null) { gen_RND = true;
    } else if (in_password.length == 0) gen_RND = true;
    if (!(this.crypt_model != CONST.crypt_NO_ENCRYPTION) ||
      (!this.mid_method.isEmpty())) gen_RND = true;
    if (gen_RND) {
      String my_RHASH = new String("");

      return my_RHASH;
    };
    String my_HASH = new String("");
    if (in_hash_size == 16) {
      my_HASH = DigestUtils.md5Hex(in_password);
    } else if (in_hash_size == 20) {
      my_HASH = DigestUtils.sha1Hex(in_password);
    } else if (in_hash_size == 28) {
      my_HASH = this.irciot_crypto_security_hasher_(in_password, "SHA-224");
    } else if (in_hash_size == 32) {
      my_HASH = DigestUtils.sha256Hex(in_password);
    } else if (in_hash_size == 48) {
      my_HASH = DigestUtils.sha384Hex(in_password);
    } else if (in_hash_size == 64) {
      my_HASH = DigestUtils.sha512Hex(in_password);
    } else if (in_hash_size == 160) {

    };
    return my_HASH;
  };
  // End of irciot_crypto_hasher_()

  public String irciot_decrypt_datum_(JSONObject in_datum,
    Sextet<String, String, String, String, Integer, Integer> in_header,
    String orig_json, String in_vuid) {
    Ennead<String, String, String, String, Integer, Integer,
      Integer, Integer, Integer> my_header;
    String my_dt = in_header.getValue0();
    String my_ot = in_header.getValue1();
    String my_src = in_header.getValue2();
    String my_dst = in_header.getValue3();
    String my_em = CONST.tag_ENC_BASE64;
    String my_enc = null;
    int my_dc = in_header.getValue4();
    int my_dp = in_header.getValue5();
    int my_bc = -1;
    int my_bp = -1;
    int my_did = -1;
    if (!in_datum.containsKey(CONST.tag_ENC_DATUM)) return "";
    if (in_datum.containsKey(CONST.tag_DATUM_BC))
      try {
        my_bc = Integer.parseInt(in_datum.get(CONST.tag_DATUM_BC).toString());
      } catch (Exception my_ex) {};
    if (in_datum.containsKey(CONST.tag_DATUM_BP))
      try {
        my_bp = Integer.parseInt(in_datum.get(CONST.tag_DATUM_BP).toString());
      } catch (Exception my_ex) {};
    if (in_datum.containsKey(CONST.tag_DATUM_ID))
      try {
        my_did = Integer.parseInt(in_datum.get(CONST.tag_DATUM_ID).toString());
      } catch (Exception my_ex) {};
    if (in_datum.containsKey(CONST.tag_ENC_METHOD))
      try {
        my_em = in_datum.get(CONST.tag_ENC_METHOD).toString();
      } catch (Exception my_ex) {};
    my_header = Ennead.with(my_dt, my_ot, my_src, my_dst,
      my_dc, my_dp, my_bc, my_bp, my_did);
    my_enc = in_datum.get(CONST.tag_ENC_DATUM).toString();
    return this.irciot_defragmentation_(my_enc, my_header, orig_json, in_vuid);
  };
  // End of irciot_decrypt_datum_()

  public String irciot_prepare_datum_(JSONObject in_datum,
  Sextet<String, String, String, String, Integer, Integer> in_header,
  String orig_json, String in_vuid) {
    JSONObject my_datum = in_datum;
    if (!in_datum.containsKey(CONST.tag_ENC_DATUM)) {
      String my_dt = in_header.getValue0();
      String my_ot = in_header.getValue1();
      String my_src = in_header.getValue2();
      String my_dst = in_header.getValue3();
      int my_dc = in_header.getValue4();
      int my_dp = in_header.getValue5();
      if (!in_datum.containsKey(CONST.tag_DATE_TIME))
        my_datum.put(CONST.tag_DATE_TIME, my_dt);
      if (!in_datum.containsKey(CONST.tag_OBJECT_TYPE))
        my_datum.put(CONST.tag_OBJECT_TYPE, my_ot);
      if (!in_datum.containsKey(CONST.tag_SRC_ADDR))
        my_datum.put(CONST.tag_SRC_ADDR, my_src);
      if (!in_datum.containsKey(CONST.tag_DST_ADDR))
        my_datum.put(CONST.tag_DST_ADDR, my_dst);
      try {
        my_dt = my_datum.get(CONST.tag_DATE_TIME).toString();
      } catch (Exception my_ex) { my_dt = null; };
      if (my_dt == null) try {
        my_datum.remove(CONST.tag_DATE_TIME);
      } catch (Exception my_ex) {};
     } else return this.irciot_decrypt_datum_(in_datum, in_header,
         orig_json, in_vuid);
    return my_datum.toString();
  };
  // End of irciot_prepare_datum_()

  // incomplete
  public void irciot_check_datum_(JSONObject in_datum,
    String in_vuid, String in_ot) {
    if (in_vuid == null) return;
    if (!in_datum.containsKey(CONST.tag_DATUM_ID)) return;
    if (!in_datum.containsKey(CONST.tag_DATE_TIME)) return;
    if ((in_ot.equals(CONST.ot_BCH_INFO)) ||
        (in_ot.equals(CONST.ot_BCH_ACK))) {
      if (in_ot.equals(CONST.ot_ENC_ACK)) {
        // It is necessary to check whether the request
        // (ot == "encreq") was, or is it a fake answer
      };

    } else if (in_ot.equals(CONST.ot_BCH_REQUEST)) {

    } else if ((in_ot.equals(CONST.ot_ENC_INFO)) ||
        (in_ot.equals(CONST.ot_ENC_ACK))) {

    } else if (in_ot.equals(CONST.ot_ENC_REQUEST)) {
      // this.irciot_encryption_key_publication_(
      //   this.encryption_public_key, CONST.ot_ENC_ACK, in_vuid);
    };
  };
  // End of irciot_check_datum_()

  //

  //
  public String irciot_crypto_wo_encryption_(String in_crypt_method) {
    int my_base = irciot_crypto_get_base_(in_crypt_method);
    int my_compress = this.irciot_crypto_get_compress_(in_crypt_method);
    if (my_base == CONST.base_BASE32)
      if (my_compress == CONST.compress_ZLIB)
        return CONST.tag_ENC_B32_ZLIB;
      else if (my_compress == CONST.compress_BZIP2)
        return CONST.tag_ENC_B32_BZIP2;
      else return CONST.tag_ENC_BASE32;
    else if (my_base == CONST.base_BASE64)
      if (my_compress == CONST.compress_ZLIB)
        return CONST.tag_ENC_B64_ZLIB;
      else if (my_compress == CONST.compress_BZIP2)
        return CONST.tag_ENC_B64_BZIP2;
      else if (my_base == CONST.base_BASE85)
        if (my_compress == CONST.compress_ZLIB)
          return CONST.tag_ENC_B85_ZLIB;
        else if (my_compress == CONST.compress_BZIP2)
          return CONST.tag_ENC_B85_BZIP2;
        else return CONST.tag_ENC_BASE85;
    return CONST.tag_ENC_BASE64;
  };
  // End of irciot_crypto_wo_encryption_()

  public int irciot_crypto_get_model_(String in_crypt_method) {
    if (Arrays.asList(CONST.tag_ALL_nocrypt_ENC).contains(in_crypt_method)) {
      return CONST.crypt_NO_ENCRYPTION;
    } else {
      int my_algo = this.irciot_crypto_get_algorithm_(in_crypt_method);
      if (Arrays.asList(CONST.crypto_ALL_asymmetric).contains(my_algo))
        return CONST.crypt_ASYMMETRIC;
      else if (Arrays.asList(CONST.crypto_ALL_symmetric).contains(my_algo))
        return CONST.crypt_SYMMETRIC;
      else if (Arrays.asList(CONST.crypto_ALL_private_key).contains(my_algo))
        return CONST.crypt_PRIVATE_KEY;
    };
    return this.crypt_model;
  };
  // End of irciot_crypto_get_model_()

  public int irciot_crypto_get_base_(String in_crypt_method) {
    if (Arrays.asList(CONST.tag_ALL_BASE32_ENC).contains(in_crypt_method))
      return CONST.base_BASE32; else
    if (Arrays.asList(CONST.tag_ALL_BASE64_ENC).contains(in_crypt_method))
      return CONST.base_BASE64; else
    if (Arrays.asList(CONST.tag_ALL_BASE85_ENC).contains(in_crypt_method))
      return CONST.base_BASE85; else
    if (Arrays.asList(CONST.tag_ALL_BASE122_ENC).contains(in_crypt_method))
      return CONST.base_BASE122;
    return this.crypt_base;
  };
  // End of irciot_crypto_get_base_()

  public int irciot_crypto_get_compress_(String in_crypt_method) {
    if (Arrays.asList(CONST.tag_ALL_nocompres_ENC).contains(in_crypt_method))
      return CONST.compress_NONE; else
    if (Arrays.asList(CONST.tag_ALL_ZLIB_ENC).contains(in_crypt_method))
      return CONST.compress_ZLIB; else
    if (Arrays.asList(CONST.tag_ALL_BZIP2_ENC).contains(in_crypt_method))
      return CONST.compress_BZIP2;
    return this.crypt_compress;
  };
  // End of irciot_crypto_get_compress_()

  public int irciot_crypto_get_algorithm_(String in_crypt_method) {
    if (Arrays.asList(CONST.tag_ALL_RSA_ENC).contains(in_crypt_method))
      return CONST.crypto_RSA;
    else if (Arrays.asList(CONST.tag_ALL_AES_ENC).contains(in_crypt_method))
      return CONST.crypto_AES;
    else if (Arrays.asList(CONST.tag_ALL_2FISH_ENC).contains(in_crypt_method))
      return CONST.crypto_2FISH;
    return this.crypt_algo;
  };
  // End of irciot_crypto_get_algorithm_()

  // incomplete
  public void irciot_blockchain_key_publication_(String in_public_key,
    String in_ot, String in_vuid) {

  };
  // End of irciot_blockchain_key_publication_()

  public boolean is_irciot_address_(String in_address) {
   if (in_address.isEmpty()) return true;
   if (in_address.contains("/")) {
     String[] my_arr = in_address.split("/");
     for (int my_idx = 0;my_idx < my_arr.length;my_idx++) {
       String my_cell = my_arr[my_idx];
       if (!this.is_irciot_address_(my_cell))
         return false;
     };
   } else if (in_address.contains("@")) {
     String[] my_arr = in_address.split("@");
     if (my_arr.length != 2) return false;
     for (int my_part = 0;my_part < 2;my_part++) {
       String my_str = my_arr[my_part];
       for (int my_idx = 0;my_idx < my_str.length();my_idx++) {
         String my_chs = my_str.charAt(my_idx) + "";
         if (!CONST.irciot_chars_addr_cell.contains(my_chs))
           return false;
       };
     };
   };
   return true;
  }
  // End of is_irciot_address_()

  // incomplete
  public Pair<String, Integer> irciot_encap_bigdatum_(JSONArray in_datums, int in_part) {
    String save_mid = this.current_mid;
    String big_ot = (String) null;
    JSONArray my_datums = in_datums;
    Iterator<?> my_iterator = my_datums.iterator();
    JSONObject big_datum = new JSONObject();
    int my_datums_cnt = 0;
    while (my_iterator.hasNext()) {
      Object my_datum_obj = (Object) my_iterator.next();
      JSONObject my_datum = (JSONObject) null;
      if (my_datum_obj instanceof JSONObject) {
        my_datum = (JSONObject) my_datum_obj;
        if ((my_datums_cnt == 0) && (my_datum != null)) {
          big_datum = my_datum;
          Object my_object = my_datum.get(CONST.tag_OBJECT_TYPE);
          if (my_object instanceof String)
            big_ot = (String) my_object;
          in_datums.remove(my_datums_cnt);
        };
      };
      my_datums_cnt += 1;
    }; // while my_iterator
    if (big_ot == null)
      return Pair.with("", 0);
    String str_big_datum = big_datum.toJSONString();
    if (this.crypt_compress == CONST.compress_ZLIB) {
      if (jlayerirciot.DO_auto_compress && this.crypt_ZLIB == null) {};
        this.irciot_load_compression_methods_(this.crypt_method);
      if (this.crypt_ZLIB == null) return Pair.with("", 0);

    } else if (this.crypt_compress == CONST.compress_BZIP2) {
      //

    } else if (this.crypt_compress == CONST.compress_NONE) {
      //

    } else return Pair.with("", 0);
    //

    return Pair.with("", 0);
  };
  // End of irciot_encap_bigdatum_()

  // incomplete
  public List<String> irciot_get_vuid_list_(String in_vuid) {
    List<String> my_vuid_list = new ArrayList<>();;

    return my_vuid_list;
  };

  // incomplete
  public String irciot_encap_internal_(String in_datumset) {
    try {
      JSONParser my_parser = new JSONParser();
      JSONObject my_datums = (JSONObject) my_parser.parse(in_datumset);
    } catch (Exception my_ex) {
      my_ex.printStackTrace();
      return "";
    };
    String my_irciot = "";


    return my_irciot;
  };
  // End of irciot_encap_internal_()

  // incomplete
  public void irciot_blockchain_check_publication_() {
    if (this.blockchain_key_published > 0) return;
    if (this.mid_method != CONST.tag_mid_ED25519
     && this.mid_method != CONST.tag_mid_RSA1024
     && this.mid_method != CONST.tag_mid_GOST12) return;
    this.blockchain_key_published = CONST.BCHT;
    // this.irciot_blockchain_key_publication_(
    //   this.blockchain_public_key, CONST.ot_BCH_INFO
    // );

  };

  // incomplete
  public List<Pair<String, String>>
    irciot_blockchain_request_to_messages_(String in_vuid) {
    JSONObject my_datum = new JSONObject();
    my_datum.put(CONST.tag_OBJECT_TYPE, CONST.ot_BCH_REQUEST);
    my_datum.put(CONST.tag_DATUM_ID, this.random.nextInt(899) + 100);
    my_datum.put(CONST.tag_BCH_METHOD, this.mid_method);

    my_datum.put(CONST.tag_SRC_ADDR, "");
    my_datum.put(CONST.tag_DST_ADDR, "");
    // Copy destination address from last message source address?!
    my_datum.put(CONST.tag_DATE_TIME,
      this.irciot_get_current_datetime_());
    return this.irciot_encap_all_(my_datum, in_vuid);
  };
  // End of irciot_blockchain_request_to_message_()

  // incomplete
  public List<Pair<String, String>>
    irciot_encryption_request_to_messages_(String in_vuid) {
    JSONObject my_datum = new JSONObject();
    my_datum.put(CONST.tag_OBJECT_TYPE, CONST.ot_ENC_REQUEST);
    my_datum.put(CONST.tag_DATUM_ID, this.random.nextInt(899) + 100);
    my_datum.put(CONST.tag_ENC_METHOD, this.crypt_method);

    my_datum.put(CONST.tag_SRC_ADDR, "");
    my_datum.put(CONST.tag_DST_ADDR, "");
    // Copy destination address from last message source address?!
    my_datum.put(CONST.tag_DATE_TIME,
      this.irciot_get_current_datetime_());
    return this.irciot_encap_all_(my_datum, in_vuid);
  };
  // End of irciot_encryption_request_to_message_()

  // incomplete
  public List<Pair<String, String>>
    irciot_blockchain_key_to_messages_(String in_key_string,
      String in_ot, String in_vuid) {

    JSONObject my_datum = new JSONObject();
    my_datum.put(CONST.tag_OBJECT_TYPE, in_ot);
    my_datum.put(CONST.tag_DATUM_ID, this.random.nextInt(899) + 100);
    my_datum.put(CONST.tag_BCH_METHOD, this.mid_method);
    my_datum.put(CONST.tag_BCH_PUBKEY, in_key_string);
    my_datum.put(CONST.tag_SRC_ADDR, "");
    my_datum.put(CONST.tag_DST_ADDR, "");
    my_datum.put(CONST.tag_DATE_TIME,
      this.irciot_get_current_datetime_());
    return this.irciot_encap_all_(my_datum, in_vuid);
  };
  // End of irciot_blockcahin_key_to_message_()

  // incomplete
  public List<Pair<String, String>>
    irciot_encryption_key_to_messages_(String in_key_string,
      String in_ot, String in_vuid) {

    JSONObject my_datum = new JSONObject();
    my_datum.put(CONST.tag_OBJECT_TYPE, in_ot);
    my_datum.put(CONST.tag_DATUM_ID, this.random.nextInt(899) + 100);
    my_datum.put(CONST.tag_ENC_METHOD, this.crypt_method);
    my_datum.put(CONST.tag_ENC_PUBKEY, in_key_string);
    my_datum.put(CONST.tag_SRC_ADDR, "");
    my_datum.put(CONST.tag_DST_ADDR, "");
    my_datum.put(CONST.tag_DATE_TIME,
      this.irciot_get_current_datetime_());
    return this.irciot_encap_all_(my_datum, in_vuid);
  };
  // End of irciot_encryption_key_to_messages

  // incomplete
  public void irciot_encryption_check_publication_() {
    if (this.encryption_key_published > 0) return;
    if (this.crypt_model != CONST.crypt_ASYMMETRIC) return;
    this.encryption_key_published = CONST.ENCT;
    // self.irciot_encryption_key_publication_(
    //   this.encryption_public_key, CONST.ot_ENC_INFO
    // );

  };

  // incomplete
  public void irciot_blockchain_place_key_to_repo_(String in_public_key) {
    if (in_public_key == null) return;

  };

  // incomplete
  public void irciot_encryption_place_key_to_repo_(String in_public_key) {
    if (in_public_key == null) return;

  };

  // incomplete
  public void irciot_blockchain_request_foreign_key_(String in_vuid) {
    // IRC-IoT messages of this type should be sent directly
    // to the user using private sending, but now they are passed
    // to the common message flow and appear in the channel
    List<Pair<String, String>> my_packs
      = this.irciot_blockchain_request_to_messages_(in_vuid);
    if (my_packs.size() == 0) return;
    Pair<String, String> my_compat = this.irciot_compatibility_();

  };
  // End of irciot_blockchain_request_foreign_key_()

  // incomplete
  public void irciot_encryption_request_foreign_key_(String in_vuid) {
    List<Pair<String, String>> my_packs
      = this.irciot_encryption_request_to_messages_(in_vuid);
    if (my_packs.size() == 0) return;
    Pair<String, String> my_compat = this.irciot_compatibility_();

  };
  // End of irciot_encryption_request_foreign_key_()

  public List<Pair<String, String>>
   irciot_encap_all_(JSONObject in_datumset, String in_vuid) {
    List<Pair<String, String>> my_out = new ArrayList<>();
    if (in_vuid == null) return my_out;
    if (in_vuid.isEmpty()
     || in_vuid.charAt(0) == CONST.api_vuid_all
     || in_vuid.charAt(0) == CONST.api_vuid_cfg
     || in_vuid.charAt(0) == CONST.api_vuid_tmp) {
      this.irciot_blockchain_check_publication_();
      this.irciot_encryption_check_publication_();
    };
    Triplet<String, Integer, Integer> my_encap;
    String json_text = "";
    int my_skip = 0;
    int my_part = 0;
    if (this.crypt_model == CONST.crypt_NO_ENCRYPTION) {
      if (!in_vuid.isEmpty()) {
        if (in_vuid.charAt(0) == CONST.api_vuid_all) {
          List<Pair<String, String>> my_inside
            = this.irciot_encap_all_(in_datumset, "");
          Iterator<Pair<String, String>> my_iterator = my_inside.iterator();
          while (my_iterator.hasNext()) {
            Pair<String, String> my_item = my_iterator.next();
            json_text = my_item.getValue0();
            my_out.add(Pair.with(json_text, in_vuid));
          };
          return my_out;
        };
      };
      // If the message is to be encrypted with end-to-end encryption
      // then it is need to create a separate message for each VUID
      // Also, the same when no encryption but type of VUID is defined
      List<String> my_vuid_list = this.irciot_get_vuid_list_(in_vuid);
      if (my_vuid_list.size() == 0) return my_out;
      Iterator<String> my_iterator = my_vuid_list.iterator();
      while (my_iterator.hasNext()) {
        String my_vuid = my_iterator.next();
        if (in_vuid.equals(CONST.api_vuid_cfg)
         || in_vuid.equals(CONST.api_vuid_tmp))
          if (my_vuid.charAt(0) == in_vuid.charAt(0)) continue;
        my_out.addAll(this.irciot_encap_all_(in_datumset, my_vuid));
      };
      return my_out;
    };
    String my_datumset_str = in_datumset.toString();
    my_encap = this.irciot_encap_(my_datumset_str, 0, 0, in_vuid);
    json_text = my_encap.getValue0();
    my_skip = my_encap.getValue1();
    my_part = my_encap.getValue2();
    if (!json_text.isEmpty()) my_out.add(Pair.with(json_text, in_vuid));
    while (my_skip > 0 || my_part > 0) {
      my_encap = this.irciot_encap_(my_datumset_str, my_skip, my_part, in_vuid);
      json_text = my_encap.getValue0();
      my_skip = my_encap.getValue1();
      my_part = my_encap.getValue2();
      if (!json_text.isEmpty()) my_out.add(Pair.with(json_text, in_vuid));
    };
    return my_out;
  };
  // End of irciot_encap_all_()

  public Triplet<String, Integer, Integer> irciot_encap_(String in_datumset,
   int in_skip, int in_part, String in_vuid) {
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
      my_json_obj = my_parser.parse(my_datumset);
    // } catch (ParseException my_ex) {
    } catch (Exception my_ex) {
      my_ex.printStackTrace();
      return Triplet.with("", 0, 0);
    };
    int my_total = 0;
    if (my_json_obj instanceof JSONArray) {
      my_datums = (JSONArray) my_json_obj;
      my_total = my_datums.size();
    } else return Triplet.with("", 0, 0);
    if (in_skip > 0) {
      my_datums_cnt = 0;
      JSONArray my_datums_array = (JSONArray) null;
      Iterator<?> my_iterator = my_datums.iterator();
      while (my_iterator.hasNext()) {
        my_datums_cnt += 1;
        Object my_datum_obj = (Object) my_iterator.next();
        JSONObject my_datum_json = (JSONObject) null;
        if (my_datum_obj instanceof JSONObject) {
          my_datum_json = (JSONObject) my_datum_obj;
          if ((my_datums_cnt > in_skip) && (my_datum_json != null))
            my_datums_array.add(my_datum_json);
        };
      }; // while my_iterator
      my_datumset = my_datums_array.toString();
      try {
        my_datums = (JSONArray) my_parser.parse(my_datumset);
      // } catch (ParseException my_ex) {
      } catch (Exception my_ex) {
        return Triplet.with("", 0, 0);
      };
    }; // in_skip
    my_irciot = this.irciot_encap_internal_(my_datumset);
    if ((my_irciot.length() > this.message_mtu) || my_encrypt) {
      if (in_skip == 0)
        this.current_mid = save_mid; // mid rollback
      try {
        my_json_obj = my_parser.parse(my_datumset);
      // } catch (ParseException my_ex) {
      } catch (Exception my_ex) {
        my_ex.printStackTrace();
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
            Iterator<?> my_iterator = my_datums.iterator();
            my_datums_cnt = 0;
            while (my_iterator.hasNext()) {
              JSONObject my_datum_json = (JSONObject) null;
              Object my_datum_obj = (Object) my_iterator.next();
              if (my_datum_obj instanceof JSONObject) {
                my_datum_json = (JSONObject) my_datum_obj;
                if ((my_datums_cnt < my_datums_skip) && (my_datum_json != null))
                  part_datums.add(my_datum_json);
              };
            }; // while my_iterator
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
          }; // while my_irciot & my_datums_skip
          one_datum = 1; // Multidatum, but current "Datum" is too large
        } else
          one_datum = 1; // One "Datum" in list, and it is too large
      }; // if my_json_obj is JSONArray
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
      this.current_did += 1; // Default Datum ID changing method
    };
    if (my_datums_part == 0)
      this.current_oid += 1;
    if (in_skip + my_datums_skip >= my_total) {
      in_skip = 0;
      my_datums_skip = 0;
      if (CAN_encrypt_datum && my_datums_part == 0) {
        // this.crypt_cache = null;
      };
    };
    return Triplet.with(my_irciot, in_skip + my_datums_skip, my_datums_part);
  };
  // End of irciot_encap_()

}
