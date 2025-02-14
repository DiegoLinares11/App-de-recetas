package com.example.appderecetas.di
import android.app.Application
import androidx.test.espresso.core.internal.deps.dagger.Module
import androidx.test.espresso.core.internal.deps.dagger.Provides
import com.example.appderecetas.data.dao.RecipeDao
import com.example.appderecetas.data.database.AppDatabase
import javax.inject.Singleton

/*

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()
}

 */