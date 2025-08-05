package com.sumit.handymany.user.service;

import com.sumit.handymany.user.dtos.CompleteWorkerProfile;
import com.sumit.handymany.user.dtos.UserDto;
import com.sumit.handymany.user.dtos.WorkerDto;
import com.sumit.handymany.user.dtos.WorkerProfileResponse;
import com.sumit.handymany.user.model.Role;
import com.sumit.handymany.user.model.User;
import com.sumit.handymany.user.model.Worker;
import com.sumit.handymany.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final ModelMapper mapper;

    public WorkerProfileResponse createWorkerProfile(Long userId, CompleteWorkerProfile request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with phone: " + userId));
        if(user.getWorkerProfile()!= null){
             throw  new RuntimeException("Profile Already exist");
        }
        Worker worker  = new Worker();
        worker.setUser(user);
        worker.setProfession(request.getProfession());
        worker.setHourlyRate(request.getHourlyRate());
        worker.setExperienceYears(request.getExperienceYears());
        worker.setSkills(request.getSkills());
       //do later fix this dynamic roles
        Role workerRole = roleService.getOrCreateRole("WORKER");
        user.getRoles().add(workerRole);

        user.setWorkerProfile(worker);
        userRepo.save(user);

        UserDto userDto = mapper.map(user, UserDto.class);

        WorkerDto workerDto = mapper.map(worker, WorkerDto.class);


        return new WorkerProfileResponse(userDto, workerDto);
    }
}
