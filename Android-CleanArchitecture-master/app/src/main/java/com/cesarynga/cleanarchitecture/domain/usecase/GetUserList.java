package com.cesarynga.cleanarchitecture.domain.usecase;

import com.cesarynga.cleanarchitecture.domain.executor.PostExecutionThread;
import com.cesarynga.cleanarchitecture.domain.executor.ThreadExecutor;
import com.cesarynga.cleanarchitecture.domain.model.User;
import com.cesarynga.cleanarchitecture.domain.repository.RepositoryCallback;
import com.cesarynga.cleanarchitecture.domain.repository.UserRepository;

import java.util.List;

public class GetUserList extends UseCase<List<User>> {

    private final UserRepository userRepository;

    public GetUserList(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    public void buildUseCase() {
        userRepository.users(new RepositoryCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> response) {
                notifyUseCaseSuccess(response);
            }

            @Override
            public void onError(Throwable exception) {
                notifyUseCaseError(exception);
            }
        });
    }
}
