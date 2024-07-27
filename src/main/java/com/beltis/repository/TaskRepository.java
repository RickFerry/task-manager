package com.beltis.repository;

import com.beltis.model.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TaskRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Task task) {
        entityManager.persist(task);
    }

    public void update(Task task) {
        entityManager.merge(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        if (task != null) {
            entityManager.remove(task);
        }
    }

    public Task findById(Long id) {
        return entityManager.find(Task.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Task> findByProjectId(Long projectId) {
        return entityManager.createQuery("FROM Task WHERE project.id = :projectId")
                .setParameter("projectId", projectId)
                .getResultList();
    }
}
