package com.print.pdf;

import com.lowagie.text.FontFactory;
import com.lowagie.text.FontFactoryImp;

import fr.opensagres.xdocreport.itext.extension.font.AbstractFontRegistry;

public abstract class ExtAbstractFontRegistry extends AbstractFontRegistry {
public static FontFactoryImp extFontFactoryImp = new ExtFontFactoryImp();

   public ExtAbstractFontRegistry()
   {
     FontFactory.setFontImp(extFontFactoryImp);
   }

}