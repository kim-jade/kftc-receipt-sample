package com.stagefive.kftcreceiptsample.dto.cms.EI13;

import com.stagefive.kftcreceiptsample.util.Util;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Builder
@AllArgsConstructor
public class EI13FileDTO {
  private EI13HeaderDTO header;
  private List<EI13DataDTO> EI13DataDTOList;
  private EI13TrailerDTO trailer;

  public byte[] getByte() {
    byte[] headerBytes = header.getByte();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    EI13DataDTOList.stream().filter(Objects::nonNull)
        .forEach(EI13Data -> out.write(EI13Data.getByte(), 0, EI13Data.getByte().length));
    byte[] dataBytes = out.toByteArray();
    byte[] trailerBytes = trailer.getByte();

    return Util.concat(headerBytes, dataBytes, trailerBytes);
  }
}
