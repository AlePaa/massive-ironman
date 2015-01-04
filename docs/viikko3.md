# Viikko 3

Selvästi liian vähän aikaa käytetty projektiin.
Mitä tehty tällä viikolla:
- Tornin kääntyminen, vaatii vielä säätöä
- Skannaus, tällä hetkellä ultraäänisensori ottaa kaksi etäisyyttä ja tutkii niiden erotusta. Hienostuneempi kaava ehkä tarpeellinen.

Demoa varten tulee olla valmiina:
- Kahta sensoria käyttävä torni.
- Kohteen seurausalgoritmi.*

*Todennäköisesti toimii näin:
	1. Kun toisessa sensorissa havaitaan liikettä, tutkitaan liikkuiko kohde toisen sensorin alueelle.
		1. Jos liikkui, käännytään toisen sensorin suuntaan
		2. Muuten käännytään toiseen suuntaan.
	2. Jatketaan kääntymistä kunnes kohde on taas sensorin alla tai ohittanut sen**
	
**Tarvitaan ehkä kohdetta kuvaava luokka, jossa säilytetään kohteen havaintoihin liittyvää tietoa tornin kääntymisnopeuden ja suunnan arvioimiseksi.