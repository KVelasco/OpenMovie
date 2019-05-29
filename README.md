
# OpenMovie  (work-in-progress)

OpenMovie is a **work-in-progress** Android app, which connects to
[TMDb](https://developers.themoviedb.org/3/getting-started/introduction). It is still in its early stages of development. 

## Android development

OpenMovie is an app which attempts to use the latest cutting edge libraries and tools. As a summary:

 * Entirely written in [Kotlin](https://kotlinlang.org/)
 * Uses [RxJava2](https://github.com/ReactiveX/RxJava)
 * Uses all of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/): Room, LiveData and Lifecycle-components
 * Uses [Dagger](https://dagger.dev/) for dependency injection
 * Uses [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
 * Uses [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)

## Development setup

First off, you require the latest Android Studio 3.0 (or newer) to be able to build the app.

### API keys

You need to supply an API Key. The app uses [TMDb](https://developers.themoviedb.org/3/getting-started/introduction) 

When you obtain the keys, you can provide them to the app by putting the following in the
`gradle.properties` file in your user home:

```
# Get this from TMDb
TMDB_API_KEY=<insert>
```

On Linux/Mac that file is typically found at `~/.gradle/gradle.properties` or in the project directory `tivi/gradle.properties`