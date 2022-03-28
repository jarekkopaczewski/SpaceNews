# Table of contents
* [General info](#general-info)
* [Main page](#main-page)
* [News page](#news-page)
* [Liked page](#liked-page)
* [Article page](#article-page)
* [Built With](#built-with)
* [Example of database connection](#example-of-database-connection)
* [License](#license)
# About The Project

The application is used to view articles about space downloaded from a database. App use API(https://spaceflightnewsapi.net/) to get articles data and photos. Access to the database is done by Volley library that invoke SQL queries and return responses as a JSON array/object. The application uses the GSON library to process responses to the objects and Picasso library to process photos.

## General info

### Main page
<img src = "https://github.com/jarekkopaczewski/SpaceNews/blob/2be2206b7bef6e45a3a2d1ee08157029887b4b55/ss_3.jpg" width = "300"/>

### News page
<img src = "https://github.com/jarekkopaczewski/SpaceNews/blob/2be2206b7bef6e45a3a2d1ee08157029887b4b55/ss_2.jpg" width = "300"/>

### Liked page
<img src = "https://github.com/jarekkopaczewski/SpaceNews/blob/2be2206b7bef6e45a3a2d1ee08157029887b4b55/ss_1.jpg" width = "300"/>

### Article page
<img src = "https://github.com/jarekkopaczewski/SpaceNews/blob/2be2206b7bef6e45a3a2d1ee08157029887b4b55/ss_4.jpg" width = "300"/>

## Built With

* [Kotlin](https://kotlinlang.org/)
* [Gradle](https://gradle.org/)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Volley](https://github.com/google/volley)
* [Gson](https://github.com/google/gson)
* [Picasso](https://github.com/square/picasso)
## Example of database connection

```kotlin
private fun makeUrlRequest()
{
    val request = StringRequest(Request.Method.GET, "https://api.spaceflightnewsapi.net/v3/articles/$idParam",
        {
            message = Gson().fromJson(it, Message::class.java)
            Picasso.get().load(message.imageUrl).into(mMainImage);
            summaryText.text = message.summary
            titleText.text = message.title
            dateText.append(message.publishedAt.substring(0,10))
            editDateText.append(message.updatedAt.substring(0,10))
        })
    {
        Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show();
    }
    queue.add(request)
}
```

## License

Distributed under the Apache-2.0 License.
<p align="right">(<a href="#top">back to top</a>)</p>
