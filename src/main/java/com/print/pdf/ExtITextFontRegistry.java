package com.print.pdf;
public class ExtITextFontRegistry extends ExtAbstractFontRegistry {

  public static ExtITextFontRegistry getRegistry()
   {
       return INSTANCE;
   }
   
   @Override
   protected String resolveFamilyName(String familyName, int style)
   {
       return familyName;
   }

   private static final ExtITextFontRegistry INSTANCE = new ExtITextFontRegistry();

}