package com.beltis.repository;

import com.beltis.model.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProjectRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Project project) {
        entityManager.persist(project);
    }

    public void update(Project project) {
        entityManager.merge(project);
    }

    public void delete(Long id) {
        Project project = findById(id);
        if (project != null) {
            entityManager.remove(project);
        }
    }

    public Project findById(Long id) {
        return entityManager.find(Project.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Project> findAll() {
        return entityManager.createQuery("FROM Project").getResultList();
    }
}

