# SETUP 
ilk olarak repoyu klonlamak istediğin klasörde şu kodu çalıştır.
```sh
git clone https://github.com/esstee61/isletimSistemleri
```
Uygulamayı test etmek için otomatik bir şekilde birkaç tane dosya oluştur:
```sh
cd isletimSistemleri
```
```sh
createTestDirectory.bat
```

# AÇIKLAMA
|||
|----------|---------------|
|`Main.java`             | Uygulamanın örnek kullanımı ve test etme      |
|`FileSearch.java`       | Ana class (Dosya içinde gezinme algoritması)      |
|`FileScore.java`        | Verilen dosyayının benzerlik oranını bulma (Dosya ismi benzerlik algoritması)     |
|`FileSearchFilter.java` | Filtreleme seçenekleri için yardımcı class    |
|||
|`similarityFunctions`   | Farklı string benzerlik bulma algoritmaları     |
|     |`Test.java` algoritma hızlarını test etmek için      |
|  |`Solution.java` Bizim yazdığımız genel algoritma     |
|        |`Re.java` Tam eşleşme için çok iyi (regex'ten çok daha hızlı ve regex ile tamamiyle aynı sonuçlar)     |

# Sırasıyla Tamamlanacaklar
- algoritma
    - tüm alt klasörlerde de arama yapma
    - tam eşleşme algoritmasını ekle (Opsiyonel)
- multithread
    - 
    - 
- gui ile birleştir
