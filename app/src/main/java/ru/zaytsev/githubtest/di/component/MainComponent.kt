package ru.zaytsev.githubtest.di.component


import dagger.Component
import ru.zaytsev.githubtest.di.module.DataBaseModule
import ru.zaytsev.githubtest.di.module.NetworkModule
import ru.zaytsev.githubtest.di.module.viewModelModule.ViewModelModule
import ru.zaytsev.githubtest.ui.details.UserDetailFragment
import ru.zaytsev.githubtest.ui.edit.EditUserInfoFragment
import ru.zaytsev.githubtest.ui.main.MainFragment
import ru.zaytsev.githubtest.ui.search.SearchUsersFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DataBaseModule::class, ViewModelModule::class, NetworkModule::class])
interface MainComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(userDetailFragment: UserDetailFragment)
    fun inject(searchUsersFragment: SearchUsersFragment)
    fun inject(editUserInfoFragment: EditUserInfoFragment)

}



