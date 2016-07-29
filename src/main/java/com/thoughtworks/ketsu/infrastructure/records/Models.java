package com.thoughtworks.ketsu.infrastructure.records;

import com.google.inject.AbstractModule;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.thoughtworks.ketsu.Dao.ProductDao;
import com.thoughtworks.ketsu.Dao.UserDao;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.ProductMapper;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.UserMapper;
import com.thoughtworks.ketsu.infrastructure.repositories.ProductRepositoryImpl;
import com.thoughtworks.ketsu.infrastructure.repositories.UserRepositoryImpl;
import org.jongo.Jongo;

import java.net.UnknownHostException;
import java.util.Properties;

public class Models extends AbstractModule {
    private static final String DEFAULT_CONFIG_RESOURCE = "db.properties";

    private final String classPathResource;

    private final String environmentId;

    private final Properties properties;

    private ClassLoader resourcesClassLoader = getDefaultClassLoader();

    private ClassLoader driverClassLoader = getDefaultClassLoader();

    public Models(String environment) {
        this(environment, DEFAULT_CONFIG_RESOURCE, new Properties());
    }

    public Models(String environment, Properties properties) {
        this(environment, DEFAULT_CONFIG_RESOURCE, properties);
    }

    public Models(String environment, String classPathResource, Properties properties) {
        this.environmentId = environment;
        this.classPathResource = classPathResource;
        this.properties = properties;
    }

    @Override
    protected void configure() {
        String dbname = System.getenv().getOrDefault("MONGODB_DATABASE", "mongodb_store");
        String host = System.getenv().getOrDefault("MONGODB_HOST", "localhost");
        String port = System.getenv().getOrDefault("MONGODB_PORT", "27017");
        String username = System.getenv().getOrDefault("MONGODB_USER", "admin");
        String password = System.getenv().getOrDefault("MONGODB_PASS", "mypass");
        String connectURL = String.format(
                "mongodb://%s:%s@%s/%s",
                username,
                password,
                host,
                dbname
        );
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(
                    new MongoClientURI(connectURL));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DB db = mongoClient.getDB("mongodb_store");
        Jongo jongo = new Jongo(db);
        bind(Jongo.class).toInstance(jongo);
        bind(ProductRepository.class).to(ProductRepositoryImpl.class);
        bind(ProductMapper.class).to(ProductDao.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(UserMapper.class).to(UserDao.class);
    }

    protected final ClassLoader getResourceClassLoader() {
        return resourcesClassLoader;
    }

    private ClassLoader getDefaultClassLoader() {
        return getClass().getClassLoader();
    }
}

