package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //엔티티 매니저 팩토리는 로딩 시점에 하나만 만들어서 어플리케이션 전체에서 공유해야한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //트랜잭션 단위(쉽게 말해 DB 커넥션이라고 보면 됨)
        //엔티티 매니저는 쓰레드간에 공유하면 안된다. (사용하고 버려야 함)
        //JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //추후에는 이런 try-catch 구문이 필요가 없음 spring에서 다 해줌
        try {
            //등록
           /* Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");

            em.persist(member);*/

            //조회
            /*Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());*/

            //JPQL
            //JPA는 SQL을 추상화한 JPQL 이라는 객체 지향 쿼리 언어 제공(객체를 대상으로 하는 객체 지향 쿼리)
            //JPQL은 엔티티 객체를 대상으로 쿼리
            //SQL은 데이터베이스 테이블을 대상으로 쿼리
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    //paging(1~10)
                    /*.setFirstResult(0)
                    .setMaxResults(10)*/
                    .getResultList();
            for(Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            //삭제
            /*Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);*/

            //수정
            /*Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA1");*/

            //em.persist(findMember); -- 해당 코드가 필요없음 마치 자바 컬렉션 쓰듯이 수정이 바로 이루어짐

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
