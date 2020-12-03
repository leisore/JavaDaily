package cn.leisore._20190616;

public class SipKey {
    private final byte[] key;
    
    public SipKey(byte[] key) {
        if (key == null || key.length != 16)
            throw new RuntimeException("SipHash key must be 16 bytes");
        this.key = key;
    }
    
    long getLeftHalf() {
       return UnsignedInt64.binToIntOffset(key, 0); 
    }
    
    long getRightHalf() {
        return UnsignedInt64.binToIntOffset(key, 8); 
    }
}