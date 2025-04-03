package br.com.pesquisa_plus.pesquisa_plus.shared.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


import jakarta.persistence.LockModeType;

@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends Repository<T, ID>, QueryByExampleExecutor<T>, PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {
Optional<T> findById(ID id);

    boolean existsById(ID id);

    T getReferenceById(ID id);

    List<T> findAllById(Iterable<ID> ids);

    long count();

    void delete(T entity);

    void deleteAllInBatch(Iterable<T> entities);

    void deleteById(ID id);

    void deleteAllByIdInBatch(Iterable<ID> ids);

    void flush();


    <S extends T> S persist(S entity);

    <S extends T> S persistAndFlush(S entity);

    <S extends T> List<S> persistAll(Iterable<S> entities);

    <S extends T> List<S> peristAllAndFlush(Iterable<S> entities);

    <S extends T> S merge(S entity);

    <S extends T> S mergeAndFlush(S entity);

    <S extends T> List<S> mergeAll(Iterable<S> entities);

    <S extends T> List<S> mergeAllAndFlush(Iterable<S> entities);

    <S extends T> S update(S entity);

    <S extends T> S updateAndFlush(S entity);

    <S extends T> List<S> updateAll(Iterable<S> entities);

    <S extends T> List<S> updateAllAndFlush(Iterable<S> entities);


    T lockById(ID id, LockModeType lockMode);
    
    
    JpaEntityInformation getEntityInformation();
    

    
    Long getProximaSequecia(String nomeSequencia);
    
    public void refresh(T t) ;
    
    
}
