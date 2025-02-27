package cn.aaron911.rpc.compress;


import cn.aaron911.rpc.extension.SPI;

@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
