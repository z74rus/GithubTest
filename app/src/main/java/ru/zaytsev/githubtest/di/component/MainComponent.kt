package ru.zaytsev.githubtest.di.component


import androidx.fragment.app.Fragment
import dagger.Component
import ru.zaytsev.githubtest.di.module.DataBaseModule
import ru.zaytsev.githubtest.di.ViewModelFactory
import ru.zaytsev.githubtest.di.module.MyModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DataBaseModule::class, MyModule::class])
interface MainComponent {
    fun inject(fragment: Fragment)
}



