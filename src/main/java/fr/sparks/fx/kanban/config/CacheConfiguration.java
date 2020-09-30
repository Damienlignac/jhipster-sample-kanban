package fr.sparks.fx.kanban.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, fr.sparks.fx.kanban.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, fr.sparks.fx.kanban.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, fr.sparks.fx.kanban.domain.User.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.Authority.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.User.class.getName() + ".authorities");
            createCache(cm, fr.sparks.fx.kanban.domain.Ville.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.Ville.class.getName() + ".clients");
            createCache(cm, fr.sparks.fx.kanban.domain.Client.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.Client.class.getName() + ".projets");
            createCache(cm, fr.sparks.fx.kanban.domain.Projet.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.Projet.class.getName() + ".developpeurs");
            createCache(cm, fr.sparks.fx.kanban.domain.TypeTache.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.TypeTache.class.getName() + ".taches");
            createCache(cm, fr.sparks.fx.kanban.domain.Tache.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.Tache.class.getName() + ".developpeurs");
            createCache(cm, fr.sparks.fx.kanban.domain.Colonne.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.Colonne.class.getName() + ".taches");
            createCache(cm, fr.sparks.fx.kanban.domain.Developpeur.class.getName());
            createCache(cm, fr.sparks.fx.kanban.domain.Developpeur.class.getName() + ".projets");
            createCache(cm, fr.sparks.fx.kanban.domain.Developpeur.class.getName() + ".taches");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
