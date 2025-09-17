package com.info.demo_batch;

import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(final User user) throws Exception {
        // Simple transformation logic: convert name to uppercase
        String transformedName = user.getName().toUpperCase();
        final User transformedUser = new User();
        transformedUser.setName(transformedName);
        transformedUser.setAge(user.getAge());

        System.out.println("Converting " + user + " into " + transformedUser);
        return transformedUser;
    }
}
