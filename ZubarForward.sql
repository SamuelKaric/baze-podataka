-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ZubarTurism
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ZubarTurism
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ZubarTurism` DEFAULT CHARACTER SET utf8 ;
USE `ZubarTurism` ;

-- -----------------------------------------------------
-- Table `ZubarTurism`.`KORISNIK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`KORISNIK` (
  `idKorisnik` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(128) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Aktivan` INT(1) NOT NULL DEFAULT 1,
  `DatumPravljenja` DATE NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`KLIJENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`KLIJENT` (
  `idKorisnik` INT NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  CONSTRAINT `fk_Klijent_KORISNIK`
    FOREIGN KEY (`idKorisnik`)
    REFERENCES `ZubarTurism`.`KORISNIK` (`idKorisnik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`DRZAVA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`DRZAVA` (
  `idDrzava` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDrzava`),
  UNIQUE INDEX `Naziv_UNIQUE` (`Naziv` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`GRAD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`GRAD` (
  `idGrad` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `idDrzava` INT NOT NULL,
  `PostanskiBroj` VARCHAR(45) NULL,
  PRIMARY KEY (`idGrad`),
  INDEX `fk_GRAD_DRZAVA1_idx` (`idDrzava` ASC) VISIBLE,
  UNIQUE INDEX `PostanskiBroj_UNIQUE` (`PostanskiBroj` ASC) VISIBLE,
  CONSTRAINT `fk_GRAD_DRZAVA1`
    FOREIGN KEY (`idDrzava`)
    REFERENCES `ZubarTurism`.`DRZAVA` (`idDrzava`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`KOORDINATOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`KOORDINATOR` (
  `idKorisnik` INT NOT NULL,
  `idGrad` INT NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  INDEX `fk_KOORDINATOR_GRAD1_idx` (`idGrad` ASC) VISIBLE,
  CONSTRAINT `fk_Koordinator_KORISNIK1`
    FOREIGN KEY (`idKorisnik`)
    REFERENCES `ZubarTurism`.`KORISNIK` (`idKorisnik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_KOORDINATOR_GRAD1`
    FOREIGN KEY (`idGrad`)
    REFERENCES `ZubarTurism`.`GRAD` (`idGrad`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`ADMINISTRATOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`ADMINISTRATOR` (
  `idKorisnik` INT NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  CONSTRAINT `fk_Administrator_KORISNIK1`
    FOREIGN KEY (`idKorisnik`)
    REFERENCES `ZubarTurism`.`KORISNIK` (`idKorisnik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`REZERVACIJA_STATUS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`REZERVACIJA_STATUS` (
  `idRS` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRS`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`TRETMAN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`TRETMAN` (
  `idTretman` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Opis` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`idTretman`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`ZUBARSKA_KLINIKA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`ZUBARSKA_KLINIKA` (
  `idZK` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Aktivan` INT(1) NOT NULL,
  `Telefon` VARCHAR(13) NULL,
  `idGrad` INT NOT NULL,
  PRIMARY KEY (`idZK`),
  INDEX `fk_ZUBARSKA KLINIKA_GRAD1_idx` (`idGrad` ASC) VISIBLE,
  CONSTRAINT `fk_ZUBARSKA KLINIKA_GRAD1`
    FOREIGN KEY (`idGrad`)
    REFERENCES `ZubarTurism`.`GRAD` (`idGrad`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`CJENOVNIK_ITEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`CJENOVNIK_ITEM` (
  `Cijena` DECIMAL(6,2) NULL,
  `idTretman` INT NOT NULL,
  `idZK` INT NOT NULL,
  PRIMARY KEY (`idTretman`, `idZK`),
  INDEX `fk_CJENOVNIK_ITEM_ZUBARSKA KLINIKA1_idx` (`idZK` ASC) VISIBLE,
  CONSTRAINT `fk_CJENOVNIK_ITEM_TRETMAN1`
    FOREIGN KEY (`idTretman`)
    REFERENCES `ZubarTurism`.`TRETMAN` (`idTretman`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_CJENOVNIK_ITEM_ZUBARSKA KLINIKA1`
    FOREIGN KEY (`idZK`)
    REFERENCES `ZubarTurism`.`ZUBARSKA_KLINIKA` (`idZK`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`REZERVACIJA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`REZERVACIJA` (
  `idRezervacije` INT NOT NULL AUTO_INCREMENT,
  `VrijemeRezeracije` DATE NOT NULL,
  `VrijemeOdlaska` DATE NOT NULL,
  `CijenaTretmana` DECIMAL(6,2) NULL,
  `CijenaSmjestaja` DECIMAL(6,2) NULL,
  `Koordinator` INT NULL,
  `Status` INT NOT NULL,
  `Klijent` INT NOT NULL,
  `idTretman` INT NOT NULL,
  `idZK` INT NOT NULL,
  PRIMARY KEY (`idRezervacije`),
  INDEX `fk_REZERVACIJA_KOORDINATOR1_idx` (`Koordinator` ASC) VISIBLE,
  INDEX `fk_REZERVACIJA_REZERVACIJA_STATUS1_idx` (`Status` ASC) VISIBLE,
  INDEX `fk_REZERVACIJA_KLIJENT1_idx` (`Klijent` ASC) VISIBLE,
  INDEX `fk_REZERVACIJA_CJENOVNIK_ITEM1_idx` (`idTretman` ASC, `idZK` ASC) VISIBLE,
  CONSTRAINT `fk_REZERVACIJA_KOORDINATOR1`
    FOREIGN KEY (`Koordinator`)
    REFERENCES `ZubarTurism`.`KOORDINATOR` (`idKorisnik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_REZERVACIJA_REZERVACIJA_STATUS1`
    FOREIGN KEY (`Status`)
    REFERENCES `ZubarTurism`.`REZERVACIJA_STATUS` (`idRS`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_REZERVACIJA_KLIJENT1`
    FOREIGN KEY (`Klijent`)
    REFERENCES `ZubarTurism`.`KLIJENT` (`idKorisnik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_REZERVACIJA_CJENOVNIK_ITEM1`
    FOREIGN KEY (`idTretman` , `idZK`)
    REFERENCES `ZubarTurism`.`CJENOVNIK_ITEM` (`idTretman` , `idZK`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`POTVRDJUJE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`POTVRDJUJE` (
  `DatumPotvrdjen` DATE NOT NULL,
  `idKorisnik` INT NOT NULL,
  `idZK` INT NOT NULL,
  PRIMARY KEY (`idKorisnik`, `idZK`),
  INDEX `fk_POTVRDJUJE_ADMINISTRATOR1_idx` (`idKorisnik` ASC) VISIBLE,
  UNIQUE INDEX `DatumPotvrdjen_UNIQUE` (`DatumPotvrdjen` ASC) VISIBLE,
  INDEX `fk_POTVRDJUJE_ZUBARSKA KLINIKA1_idx` (`idZK` ASC) VISIBLE,
  CONSTRAINT `fk_POTVRDJUJE_ADMINISTRATOR1`
    FOREIGN KEY (`idKorisnik`)
    REFERENCES `ZubarTurism`.`ADMINISTRATOR` (`idKorisnik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_POTVRDJUJE_ZUBARSKA KLINIKA1`
    FOREIGN KEY (`idZK`)
    REFERENCES `ZubarTurism`.`ZUBARSKA_KLINIKA` (`idZK`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`TIP_ZUBARA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`TIP_ZUBARA` (
  `idTZ` INT NOT NULL AUTO_INCREMENT,
  `Zvanje` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTZ`),
  UNIQUE INDEX `Zvanje_UNIQUE` (`Zvanje` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`ZUBAR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`ZUBAR` (
  `idZubar` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NULL,
  `idZK` INT NOT NULL,
  `idTZ` INT NOT NULL,
  PRIMARY KEY (`idZubar`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  INDEX `fk_ZUBAR_ZUBARSKA KLINIKA1_idx` (`idZK` ASC) VISIBLE,
  INDEX `fk_ZUBAR_TIP_ZUBARA1_idx` (`idTZ` ASC) VISIBLE,
  CONSTRAINT `fk_ZUBAR_ZUBARSKA KLINIKA1`
    FOREIGN KEY (`idZK`)
    REFERENCES `ZubarTurism`.`ZUBARSKA_KLINIKA` (`idZK`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ZUBAR_TIP_ZUBARA1`
    FOREIGN KEY (`idTZ`)
    REFERENCES `ZubarTurism`.`TIP_ZUBARA` (`idTZ`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`RECENZIJA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`RECENZIJA` (
  `idRecenzija` INT NOT NULL AUTO_INCREMENT,
  `Sadrzaj` VARCHAR(1000) NULL,
  `Ocijena` DECIMAL(3,2) NULL,
  `idRezervacije` INT NOT NULL,
  PRIMARY KEY (`idRecenzija`),
  INDEX `fk_RECENZIJA_REZERVACIJA1_idx` (`idRezervacije` ASC) VISIBLE,
  CONSTRAINT `fk_RECENZIJA_REZERVACIJA1`
    FOREIGN KEY (`idRezervacije`)
    REFERENCES `ZubarTurism`.`REZERVACIJA` (`idRezervacije`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`HOTEL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`HOTEL` (
  `idHotel` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Telefon` VARCHAR(10) NULL,
  `idGrad` INT NOT NULL,
  `Aktivan` INT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idHotel`),
  INDEX `fk_HOTEL_GRAD1_idx` (`idGrad` ASC) VISIBLE,
  CONSTRAINT `fk_HOTEL_GRAD1`
    FOREIGN KEY (`idGrad`)
    REFERENCES `ZubarTurism`.`GRAD` (`idGrad`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`REZERVACIJA_HOTEL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`REZERVACIJA_HOTEL` (
  `Cijena` DECIMAL(6,2) NOT NULL,
  `idHotel` INT NOT NULL,
  `idRezervacija` INT NOT NULL,
  INDEX `fk_REZERVISANA_SOBA_HOTEL1_idx` (`idHotel` ASC) VISIBLE,
  INDEX `fk_REZERVISANA_SOBA_REZERVACIJA1_idx` (`idRezervacija` ASC) VISIBLE,
  PRIMARY KEY (`idHotel`, `idRezervacija`),
  CONSTRAINT `fk_REZERVISANA_SOBA_HOTEL1`
    FOREIGN KEY (`idHotel`)
    REFERENCES `ZubarTurism`.`HOTEL` (`idHotel`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_REZERVISANA_SOBA_REZERVACIJA1`
    FOREIGN KEY (`idRezervacija`)
    REFERENCES `ZubarTurism`.`REZERVACIJA` (`idRezervacije`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`JEZIK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`JEZIK` (
  `idJezik` INT NOT NULL,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idJezik`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ZubarTurism`.`KOORDINATOR_JEZIK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ZubarTurism`.`KOORDINATOR_JEZIK` (
  `idKorisnik` INT NOT NULL,
  `idJezik` INT NOT NULL,
  PRIMARY KEY (`idKorisnik`, `idJezik`),
  INDEX `fk_KOORDINATOR_has_JEZIK_JEZIK1_idx` (`idJezik` ASC) VISIBLE,
  INDEX `fk_KOORDINATOR_has_JEZIK_KOORDINATOR1_idx` (`idKorisnik` ASC) VISIBLE,
  CONSTRAINT `fk_KOORDINATOR_has_JEZIK_KOORDINATOR1`
    FOREIGN KEY (`idKorisnik`)
    REFERENCES `ZubarTurism`.`KOORDINATOR` (`idKorisnik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_KOORDINATOR_has_JEZIK_JEZIK1`
    FOREIGN KEY (`idJezik`)
    REFERENCES `ZubarTurism`.`JEZIK` (`idJezik`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
