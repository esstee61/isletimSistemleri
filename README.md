# Setup 
ilk olarak repoyu klonlamak istediğin klasörde şu kodu çalıştır:
```sh
git clone https://github.com/esstee61/isletimSistemleri
```
Programı test etmek için otomatik bir şekilde birkaç tane dosya oluştur:
```sh
cd isletimSistemleri
```
```sh
createTestDirectory.bat
```

# Açıklama
|||
|----------|---------------|
|`Main.java`             | Programın örnek kullanımı ve test etme      |
|`FileSearch.java`       | Ana class (Dosya içinde gezinme algoritması)      |
|`FileScore.java`        | Verilen dosyanın benzerlik oranını bulma (Dosya ismi benzerlik algoritması)     |
|`FileSearchFilter.java` | Filtreleme seçenekleri için yardımcı class    |
|||
|`similarityFunctions`   | Farklı string benzerlik bulma algoritmaları     |
|     |`Test.java` algoritma hızlarını test etmek için      |
|  |`Solution.java` yazdığımız algoritma     |
|        |`Re.java` tam eşleşme için      |

# Planlama
- **algoritma**
    - ~~tüm alt klasörlerde de arama yapma~~ | *recSearch() metodu eklendi*
    - ~~tam eşleşme algoritmasını ekle~~ | *exactMatch secenegi eklendi*
    - *İsimleri çelişen dosya hatası düzeltildi*
    - *Filterdeki onlyFilesın mantık hatası giderildi*
- **multithread**
    - Gerekiyorsa FileSearchteki results değişkeninin türünü değistir. (bknz:FileSearch.java line 18)
    - ?
- **gui ile birleştir**
    - ?