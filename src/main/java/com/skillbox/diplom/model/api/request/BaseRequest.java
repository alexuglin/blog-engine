package com.skillbox.diplom.model.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class BaseRequest {

    private int offset = 0;

    private int limit = 10;
}
