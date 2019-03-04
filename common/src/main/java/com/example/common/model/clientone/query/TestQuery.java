package com.example.common.model.clientone.query;

import com.example.common.model.clientone.valid.TestQueryValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuery {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull(groups = TestQueryValid.Post.class)
    private String value;

}
