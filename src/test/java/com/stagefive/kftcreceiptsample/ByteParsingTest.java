package com.stagefive.kftcreceiptsample;

import com.stagefive.kftcreceiptsample.dto.common.CommonLayout;

public class ByteParsingTest {
  public static void main(String[] args) {
    byte[] data = "FTEABCDEFGHABCDAAABCDEFGHABC".getBytes();
    CommonLayout commonLayout = new CommonLayout(data);
    System.out.println(commonLayout);
  }
}
