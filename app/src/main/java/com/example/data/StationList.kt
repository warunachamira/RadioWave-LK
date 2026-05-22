package com.example.data

import com.example.model.Station

object StationList {
    val ALL_STATIONS = listOf(
        Station("hiru", "Hiru FM", "http://stream.hirufm.lk:8000/hirufm", "https://wiki.hirufm.lk/assets/images/hiru-logo.png", "Sinhala", "Popular"),
        Station("sha", "Shaa FM", "http://shaa.listen.stream:8000/shaa", "https://wiki.shaafm.lk/assets/images/shaa-logo.png", "Sinhala", "Popular"),
        Station("sirasa", "Sirasa FM", "http://185.105.4.100:8000/sirasa", "https://upload.wikimedia.org/wikipedia/en/b/b8/Sirasa_FM.png", "Sinhala", "Trending"),
        Station("derana", "FM Derana", "http://stream.fmderana.lk:8000/fmderana", "https://upload.wikimedia.org/wikipedia/en/2/25/FM_Derana_Logo.png", "Sinhala", "Trending"),
        Station("siyatha", "Siyatha FM", "http://209.133.216.3:8000/siyatha", "https://cdn-profiles.tunein.com/s133202/images/logog.jpg?t=1588825129000", "Sinhala", "Popular"),
        Station("neth", "Neth FM", "http://stream.nethfm.com:8000/nethfm", "https://nethfm.com/assets/img/logo.png", "Sinhala", "News/Talk"),
        Station("yfm", "Y FM", "http://yfm.listen.stream:8000/yfm", "https://upload.wikimedia.org/wikipedia/en/thumb/f/f0/Y_FM.jpg/220px-Y_FM.jpg", "Sinhala", "Youth"),
        Station("shakthi", "Shakthi FM", "http://185.105.4.100:8000/shakthi", "https://upload.wikimedia.org/wikipedia/en/thumb/f/f6/Shakthi_FM.png/220px-Shakthi_FM.png", "Tamil", "Popular"),
        Station("sooriyan", "Sooriyan FM", "http://stream.sooriyanfm.lk:8000/sooriyanfm", "https://upload.wikimedia.org/wikipedia/en/b/b1/Sooriyan_fm_srilanka.png", "Tamil", "Trending"),
        Station("vasantham", "Vasantham FM", "http://stream.vasantham.lk:8000/vasantham", "https://upload.wikimedia.org/wikipedia/en/5/5a/Vasantham_FM_logo.png", "Tamil", "Popular"),
        Station("sun", "Sun FM", "http://stream.sunfm.lk:8000/sunfm", "https://upload.wikimedia.org/wikipedia/en/c/ca/Sun_FM.jpg", "English", "Popular"),
        Station("yes", "Yes FM", "http://185.105.4.100:8000/yes", "https://upload.wikimedia.org/wikipedia/en/thumb/f/fd/Yes_FM.png/220px-Yes_FM.png", "English", "Trending"),
        Station("kiss", "Kiss FM", "http://stream.kissfm.lk:8000/kissfm", "https://upload.wikimedia.org/wikipedia/en/f/f5/Kiss_FM_Sri_Lanka_Logo.png", "English", "Youth"),
        Station("gold", "Gold FM", "http://stream.goldfm.lk:8000/goldfm", "https://upload.wikimedia.org/wikipedia/en/4/41/Gold_FM.png", "English", "Classic"),
        Station("slbc_sinhala", "SLBC Sinhala National", "http://220.247.28.60:8000/sinhala_national", "https://upload.wikimedia.org/wikipedia/en/7/7b/SLBC_logo.jpg", "Sinhala", "SLBC"),
        Station("slbc_tamil", "SLBC Tamil National", "http://220.247.28.60:8000/tamil_national", "https://upload.wikimedia.org/wikipedia/en/7/7b/SLBC_logo.jpg", "Tamil", "SLBC"),
        Station("slbc_english", "SLBC Radio Sri Lanka", "http://220.247.28.60:8000/radio_srilanka", "https://upload.wikimedia.org/wikipedia/en/7/7b/SLBC_logo.jpg", "English", "SLBC"),
        Station("thendral", "Thendral FM", "http://220.247.28.60:8000/thendral", "https://upload.wikimedia.org/wikipedia/en/7/7b/SLBC_logo.jpg", "Tamil", "SLBC"),
        Station("city", "City FM", "http://220.247.28.60:8000/city_fm", "https://upload.wikimedia.org/wikipedia/en/7/7b/SLBC_logo.jpg", "Sinhala", "SLBC"),
        // Added standard reliable stream as fallback for testing ExoPlayer if others fail
        Station("bbc_world", "BBC World Service (Test)", "https://stream.live.vc.bbcmedia.co.uk/bbc_world_service", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/BBC_World_Service_2022.svg/300px-BBC_World_Service_2022.svg.png", "English", "Popular")
    )
}
