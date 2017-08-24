package com.umanteam.dadakar.run.back.service.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umanteam.dadakar.back.dto.UserDTO;
import com.umanteam.dadakar.back.dto.VehicleDTO;
import com.umanteam.dadakar.back.entities.User;
import com.umanteam.dadakar.back.entities.Vehicle;
import com.umanteam.dadakar.run.back.dto.RunDTO;
import com.umanteam.dadakar.run.back.entities.Run;
import com.umanteam.dadakar.run.back.repository.RunRepository;
import com.umanteam.dadakar.run.back.service.interfaces.IRunService;

@Service
public class RunService implements IRunService {

	@Autowired
	RunRepository runRepository;
	
	@Override
	public RunDTO addRun(RunDTO run) {
		Run entity = new Run();
		BeanUtils.copyProperties(run, entity);
		User userEntity = new User();
		BeanUtils.copyProperties(run.getDriver(), userEntity);
		entity.setDriver(userEntity);
		Vehicle vehicleEntity = new Vehicle();
		BeanUtils.copyProperties(run.getVehicle(), vehicleEntity);
		entity.setVehicle(vehicleEntity);
		// SubRun copy
		// save
		entity = runRepository.insert(entity);
		BeanUtils.copyProperties(entity, run);
		return run;
	}

	@Override
	public RunDTO updateRun(RunDTO run) {
		// copy run dto to entity
		Run entity = new Run();
		BeanUtils.copyProperties(run, entity);
		BeanUtils.copyProperties(entity, run);
		// copy driver to entity and assign to run entity
		User userEntity = new User();
		BeanUtils.copyProperties(run.getDriver(), userEntity);
		entity.setDriver(userEntity);
		// copy vehicle to entity and assign to run entity
		Vehicle vehicleEntity = new Vehicle();
		BeanUtils.copyProperties(run.getVehicle(), vehicleEntity);
		entity.setVehicle(vehicleEntity);
		// TODO SubRun copy
		// save
		entity = runRepository.save(entity);
		return run;
	}

	@Override
	public void deleteRun(String id) {
		runRepository.delete(id);
	}

	@Override
	public List<RunDTO> findAllRuns() {
		List<RunDTO> runs = new ArrayList<>();
		for (Run entity : runRepository.findAll()){
			// copy run entity to DTO
			RunDTO run = new RunDTO();
			BeanUtils.copyProperties(entity, run);
			// copy driver entity to DTO and assign to run 
			if (entity.getDriver() != null){
				UserDTO user = new UserDTO();
				BeanUtils.copyProperties(entity.getDriver(), user);
				run.setDriver(user);
			}
			// copy vehicle entity to dto and assign to run
			if(entity.getVehicle() != null){
				VehicleDTO vehicle = new VehicleDTO();
				BeanUtils.copyProperties(entity.getVehicle(), vehicle);
				run.setVehicle(vehicle);
			}
			// TODO subrun copy
			// add run to list
			runs.add(run);
		}
		return runs;
	}

	@Override
	public RunDTO findRunsById(String id) {
		RunDTO run = new RunDTO();
		Run entity = runRepository.findOne(id);
		BeanUtils.copyProperties(entity, run);
		// copy driver entity to DTO and assign to run 
		if (entity.getDriver() != null){
			UserDTO user = new UserDTO();
			BeanUtils.copyProperties(entity.getDriver(), user);
			run.setDriver(user);
		}
		// copy vehicle entity to dto and assign to run
		if(entity.getVehicle() != null){
			VehicleDTO vehicle = new VehicleDTO();
			BeanUtils.copyProperties(entity.getVehicle(), vehicle);
			run.setVehicle(vehicle);
		}
		// TODO subrun copy
		return run;
	}

	@Override
	public List<RunDTO> findRunsByDriver(UserDTO driver) {
		User driverEntity = new User();
		BeanUtils.copyProperties(driver, driverEntity);
		List<RunDTO> runs = new ArrayList<>();
		for (Run entity : runRepository.findByDriver(driverEntity)){
			RunDTO run = new RunDTO();
			BeanUtils.copyProperties(entity, run);
			// copy driver entity to DTO and assign to run 
			if (entity.getDriver() != null){
				UserDTO user = new UserDTO();
				BeanUtils.copyProperties(entity.getDriver(), user);
				run.setDriver(user);
			}
			// copy vehicle entity to dto and assign to run
			if(entity.getVehicle() != null){
				VehicleDTO vehicle = new VehicleDTO();
				BeanUtils.copyProperties(entity.getVehicle(), vehicle);
				run.setVehicle(vehicle);
			}
			// TODO subrun copy
			runs.add(run);
		}
		return runs;
	}

	@Override
	public List<RunDTO> findRunsNotCancelledByDriver(UserDTO driver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findRunsByPassenger(UserDTO passenger) {
		User passengerEntity = new User();
		BeanUtils.copyProperties(passenger, passengerEntity);
		List<RunDTO> runs = new ArrayList<>();
		// TODO : implement method when subrun available
//		for (Run entity : runRepository.findBySubRunPassengerUserExists(passengerEntity)) {
//			if passenger not refused, canceled or run-canceled
//				RunDTO run = new RunDTO();
//				BeanUtils.copyProperties(entity, run);
//				runs.add(run);
//		}
		return runs;
	}

	@Override
	public List<RunDTO> findRunsNotCancelledByPassenger(UserDTO passenger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findRunsByUser(UserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findRunsNotCancelledByUser(UserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findCurrentRunsbyUser(UserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findCurrentRunsNotCancelledByUser(UserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findPassedRunsbyUser(UserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findPassedRunsNotCancelledByUser(UserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RunDTO> findRuns(String districtFrom, String townFrom, LocalDate dateStart, String districtTo,
			String townTo) {
		List<RunDTO> runs = new ArrayList<>();
		// search for runs that have a matching subrun
		// implement method when subrun available
		return null;
	}

}