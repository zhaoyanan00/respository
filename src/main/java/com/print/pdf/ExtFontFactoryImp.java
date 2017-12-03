package com.print.pdf;

import java.io.File;

import com.lowagie.text.FontFactoryImp;

public class ExtFontFactoryImp extends FontFactoryImp {
public ExtFontFactoryImp(){
    super();

}
/**
 * 注册字体（现在只有宋体）
 */
public int registerDirectories(){
       int i = 0;
       i += registerDirectory("D:"+File.separator+"font");
       return i;
}
}