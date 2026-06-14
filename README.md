# Twitter API

Bu proje, basit bir Twitter benzeri REST API uygulamasıdır. Spring Boot, Spring Data JPA ve Spring Security kullanılarak uygulanmıştır. Proje temel olarak kullanıcı kaydı/girişi, tweet oluşturma, beğeni, yorum ve retweet işlevlerini sağlar.

**Öne çıkan özellikler**

- Kullanıcı kaydı ve oturum açma (`/auth`)
- Tweet oluşturma, listeleme, güncelleme ve silme (`/tweet`)
- Tweetlere beğeni ekleme/silme (`/like`)
- Tweetlere retweet ekleme/silme (`/retweet`)
- Tweetlere yorum ekleme/güncelleme/silme (`/comment`)
- Basit form-login ve HTTP Basic destekli güvenlik; `/auth/**` uç noktaları herkese açıktır

**Teknoloji yığını**

- Java 17
- Spring Boot 4.x
- Spring Data JPA
- Spring Security
- PostgreSQL (prod), H2 (test)
- Lombok

## Proje Yapısı (kısa)

- `com.twitter.twitterapi.controller` — REST denetleyiciler (API uç noktaları)
- `com.twitter.twitterapi.service` — iş mantığı
- `com.twitter.twitterapi.repository` — JPA repository arayüzleri
- `com.twitter.twitterapi.entity` — JPA varlıkları (User, Tweet, Like, Comment, Retweet, Role)
- `src/main/resources/application.properties` — örnek konfigürasyon

## Gerekli önkoşullar

- Java 17 yüklü olmalı
- Maven (veya projedeki `mvnw`/`mvnw.cmd`) kullanılabilir
- PostgreSQL veritabanı (varsayılan ayarlar `application.properties` içinde)

## Konfigürasyon

Projede örnek `application.properties` şu öğeleri içerir: `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`, `server.port`. Bu değerleri üretim ortamında sabit dosyaya yazmak yerine ortam değişkenleri veya güvenli bir yapı ile sağlamanız önerilir.

Örnek ortam değişkenleri (opsiyonel):

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres?currentSchema=twitter
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=<your_password>
SERVER_PORT=3000
```

## Canlı Test Ortamı

Projeyi kurmadan hızlıca denemek için canlı API adresi:

https://twitter-api-8heh.onrender.com

Bu adresi Swagger veya doğrudan REST istemcisi ile test edebilirsiniz. Kimlik doğrulama gereken uç noktalar için önce `/auth/login` ya da `/auth/register` ile kullanıcı oluşturmanız gerekir.

## API Uç Noktaları (özet)

Tüm uç noktalar JSON giriş/çıkış kullanır. Kimlik doğrulama gerektiren uç noktalar için öncelikle `/auth/login` veya form-login ile oturum açılmalıdır.

- `POST /auth/register` — kullanıcı kaydı (gönderilecek: `CreateUserRequest`)
- `POST /auth/login` — giriş (gönderilecek: `LoginUserRequest`)

- `POST /tweet` — yeni tweet oluştur (gönderilecek: `CreateTweetRequest`)
- `GET /tweet` — oturum açmış kullanıcının tweetlerini getir
- `GET /tweet/{id}` — id ile tweet getir
- `PUT /tweet/{id}` — tweet güncelle
- `DELETE /tweet/{id}` — tweet sil

- `POST /retweet` — retweet ekle (`CreateRetweetRequest`)
- `DELETE /retweet/{id}` — retweet sil

- `POST /like` — like ekle (`CreateLikeRequest`)
- `DELETE /like/{tweetId}` — like sil

- `POST /comment?tweetId={tweetId}` — yorum ekle (`CreateCommentRequest`)
- `PUT /comment/{id}` — yorum güncelle (`UpdateCommentRequest`)
- `DELETE /comment/{id}` — yorum sil

Detaylı istek/alan isimleri için `src/main/java/com/twitter/twitterapi/dto/request` içindeki DTO sınıflarına bakın.

## Varlıklar (kısa)

- `User` — id, firstName, lastName, email, password, roller ve ilişkili tweet/like/comment/retweet listeleri
- `Tweet` — id, text, kullanıcı, likes, comments, retweets
- `Like` — id, user, tweet
- `Comment` — id, text, user, tweet
- `Retweet` — id, user, tweet

## Testler

Projede birim/integrasyon testleri `src/test/java` içinde mevcuttur. Testleri çalıştırmak için:

```
mvnw.cmd test
```

## Güvenlik ve dikkat edilmesi gerekenler

- `application.properties` içinde gerçek parolalar bulunmamalıdır. Ortam değişkeni veya secrets yönetimi kullanın.
- Security yapılandırmasında `/auth/**` yolları açık bırakılmış; diğer tüm istekler kimlik doğrulaması gerektirir.

## Doğrulama (Validation)

DTO sınıflarında (`src/main/java/com/twitter/twitterapi/dto/request`) `@NotBlank`, `@NotNull` gibi Jakarta Validation (Bean Validation) anotasyonları kullanılmıştır. Bu doğrulamaların etkin olması için controller metodlarınızda `@Valid` anotasyonunu kullanın. Örnek:

```
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public TweetDto save(@Valid @RequestBody CreateTweetRequest tweetRequest, Authentication authentication) {
	// ...
}
```

Not: Controller metodlarına `@Valid` eklemek ve `@RequestBody` ile birlikte kullanmak, Spring'in otomatik doğrulamasını tetikler. `BindingResult` veya özel handler'lar ile alan bazlı hata mesajlarını frontend'e iletebilirsiniz.

Genel öneriler:

- Hata mesajlarında hassas bilgi (parola, tam SQL, stack trace) döndürmeyin.
- Loglama yaparken hata ayrıntılarını (stack trace) sunucuda saklayın, API yanıtlarında kısa ve kullanıcı-dostu mesajlar verin.
- Doğrulama hatalarını `400 Bad Request` olarak, yetki/kimlik hatalarını `401/403` olarak, bulunamayan kaynakları `404` olarak döndürün.

## Katkıda bulunma

1. Fork'layın
2. Yeni bir branch açın
3. Değişiklik yapın ve testleri çalıştırın
4. Pull request gönderin

---
