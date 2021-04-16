package ru.zaytsev.githubtest.di.module.viewModelModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.zaytsev.githubtest.ui.details.UserDetailViewModel
import ru.zaytsev.githubtest.ui.edit.EditUserInfoViewModel
import ru.zaytsev.githubtest.ui.main.MainViewModel
import ru.zaytsev.githubtest.ui.search.SearchUsersViewModel
import ru.zaytsev.githubtest.utils.ViewModelKey

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    internal abstract fun bindUserDetailViewModel(viewModel: UserDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchUsersViewModel::class)
    internal abstract fun bindSearchUsersViewModel(viewModel: SearchUsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditUserInfoViewModel::class)
    internal abstract fun bindEditUserInfoViewModel(viewModel: EditUserInfoViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}