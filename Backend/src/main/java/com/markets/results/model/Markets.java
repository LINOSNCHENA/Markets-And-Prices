package com.markets.results.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MARKETS")
public class Markets {

  @Id
  @Column(name = "hour")
  private long id;

  @Column(name = "Tradedvolume")
  private double Tradedvolume;

  @Column(name = "Averageprice")
  private double Averageprice;

  @Column(name = "Minimumprice")
  private double Minimumprice;

  @Column(name = "Maximumprice")
  private double Maximumprice;

  @Column(name = "Lastprice")
  private double Lastprice;

  public Markets() {
  }

  public Markets(long id, double Tradedvolume, double Averageprice, double Maximumprice, double Minimumprice,
      double Lastprice) {
    this.id = id;
    this.Tradedvolume = Tradedvolume;
    this.Averageprice = Averageprice;
    this.Minimumprice = Minimumprice;
    this.Maximumprice = Maximumprice;
    this.Lastprice = Lastprice;
  }

  public long getHour() {
    return id;
  }

  public void setHour(long id) {
    this.id = id;
  }

  public double getTradedvolume() {
    return Tradedvolume;
  }

  public void setTradedvolume(double Tradedvolume) {
    this.Tradedvolume = Tradedvolume;
  }

  public double getAverageprice() {
    return Averageprice;
  }

  public void setAverageprice(double Averageprice) {
    this.Averageprice = Averageprice;
  }

  public double getMinimumprice() {
    return Minimumprice;
  }

  public void setMinimumprice(double Minimumprice) {
    this.Minimumprice = Minimumprice;
  }

  public double getMaximumprice() {
    return Maximumprice;
  }

  public void setMaximumprice(double Maximumprice) {
    this.Maximumprice = Maximumprice;
  }

  public double getLastprice() {
    return Lastprice;
  }

  public void setLastprice(double Lastprice) {
    this.Lastprice = Lastprice;
  }

  @Override
  public String toString() {
    return ("Markets [Hour = " + id + ", Traded Volume = " + Tradedvolume +
     ", MIN = " + Minimumprice + ", MAX = "+ Maximumprice + ", LAST = " + Lastprice + "]");
  }
}
