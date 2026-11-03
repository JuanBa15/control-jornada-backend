package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.JobTypeMapper;
import cl.bennu.controljornadabackend.model.JobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobTypeService {

    private @Autowired JobTypeMapper jobTypeMapper;

    public List<JobType> getAllJobTypes() {
        return jobTypeMapper.getAll();
    }

    @Transactional
    public void insertJobType(JobType jobType) {
        jobTypeMapper.insert(jobType);
    }

    @Transactional
    public void updateJobType(JobType jobType) {
        jobTypeMapper.update(jobType);
    }

    public JobType getJobTypeById(Long jobTypeId) {
        return jobTypeMapper.get(jobTypeId);
    }

    public void deleteJobType(Long jobTypeId) {
        jobTypeMapper.delete(jobTypeId);
    }

}
