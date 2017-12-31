package com.cesarynga.cleanarchitecture.domain.usecase;

import com.cesarynga.cleanarchitecture.domain.executor.PostExecutionThread;
import com.cesarynga.cleanarchitecture.domain.executor.ThreadExecutor;
import com.cesarynga.cleanarchitecture.domain.model.User;
import com.cesarynga.cleanarchitecture.domain.repository.RepositoryCallback;
import com.cesarynga.cleanarchitecture.domain.repository.UserRepository;

public class GetUser extends UseCase<User> {

    private final UserRepository userRepository;

    private int userId;

    public GetUser(UserRepository userRepository,
                   ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setParams(int userId) {
        this.userId = userId;
    }

    @Override
    protected void buildUseCase() {
        this.userRepository.user(this.userId, new RepositoryCallback<User>() {
            @Override
            public void onSuccess(User response) {

                notifyUseCaseSuccess(response);
            }

            @Override
            public void onError(Throwable exception) {
                notifyUseCaseError(exception);
            }
        });
    }
}
