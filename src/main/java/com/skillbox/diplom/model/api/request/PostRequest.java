package com.skillbox.diplom.model.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PostRequest extends BaseRequest {

    private String mode;
}
