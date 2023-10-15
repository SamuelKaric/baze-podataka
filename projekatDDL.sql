drop schema if exists zubarenje;

create schema zubarenje 
default character set utf8
default collate utf8_unicode_ci;

use zubarenje;

create table korisnik
(
	IdKorisnik int auto_increment, 
    Ime varchar(45) not null,
    Pass varchar(128) not null,
    Email varchar(100) not null,
    Aktivan int(1) not null,
    DatumPravljenja date not null,
    primary key(Idkorisnik)
);

create table klijent
(
	idKorisnik int auto_increment primary key,
    constraint fk_Klijent_Korisnik
    foreign key (idKorisnik)
    references korisnik (idKorisnik)
);

create table drzava
(
	IdDrzava int auto_increment primary key,
    Naziv varchar(45) not null
);

create table grad
(
	IdGrad int auto_increment,
    Naziv varchar(45) not null,
    PostanskiBroj varchar(10) not null,
    IdDrzava int not null,
    primary key (IdGrad),
    constraint fk_grad_drzava
    foreign key (IdDrzava)
	references drzava (IdDrzava)
);

create table zubarska_klinika
(
	IdZK int auto_increment,
    Naziv varchar(45) not null,
    Aktivan int(1) not null,
    Telefon varchar(13) not null,
    IdGrad int not null,
    primary key (IdZK),
    constraint fk_zk_grad
    foreign key (IdGrad)
    references grad (IdGrad)
);

create table tretman
(
	 IdTretman int auto_increment primary key,
     Naziv varchar(45) not null,
     Opis varchar(1000)
);

create table cjenovnik_item
(
	IdTretman int,
    IdZK int,
    Cijena decimal(6,2),
	primary key (IdTretman, IdZK),
    constraint fk_ci_tretman
    foreign key (IdTretman)
    references tretman (IdTretman),
    constraint fk_ci_zk
    foreign key (IdZK)
    references zubarska_klinika (IdZK)
);

create table rezervacija_status
(
	IdRezervacijaStatus int auto_increment primary key,
    Naziv varchar(45) not null
);

create table rezervacija
(
	IdRezervacija int auto_increment,
    VrijemeRezervacije date not null,
    TrajanjeDani int not null,
    CijenaTretmana decimal(5,2),
    IdTretman int not null,
    IdZK int not null,
    IdStatus int not null,
    IdKlijent int not null,
    primary key (IdRezervacija),
    constraint fk_Rezervacija_RezervacijaStatus
    foreign key (IdStatus)
    references rezervacija_status (idRezervacijaStatus),
    constraint fk_rezervacija_klijent
    foreign key (IdKlijent)
    references klijent (idKorisnik),
    constraint fk_rezervacija_tretman
    foreign key (IdTretman, IdZK)
    references cjenovnik_item (IdTretman, IdZK)
);
