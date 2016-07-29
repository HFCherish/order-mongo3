package com.thoughtworks.ketsu.support;

import com.google.inject.AbstractModule;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import org.junit.rules.TestRule;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class DatabaseTestRunner extends InjectBasedRunner {

//    @Inject
//    DB db;

    public DatabaseTestRunner(final Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected List<AbstractModule> getModules() {
        return asList(new AbstractModule() {
            @Override
            protected void configure() {;
            }
        });
    }

    private final TestRule rollbackSessionManager = (base, description) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            } finally {
//                db.getCollection("products").remove(new BasicDBObject());
//                db.getCollection("users").remove(new BasicDBObject());
//                db.getCollection("orders").remove(new BasicDBObject());
            }
        }
    };

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> rules = new ArrayList<>();
        rules.add(rollbackSessionManager);
        rules.addAll(super.getTestRules(target));
        return rules;
    }
}
