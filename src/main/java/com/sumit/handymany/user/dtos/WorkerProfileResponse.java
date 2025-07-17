package com.sumit.handymany.user.dtos;

import com.sumit.handymany.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerProfileResponse {

    private UserDto userDto;
    private WorkerDto workerDto;
}
