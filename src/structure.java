class strSell
{
    String no;
    String people;
    String phone;
    String address;
    int size;
    int rent;
    String pic;
    int price;
    String status;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getNo()
    {
        return no;
    }

    public String getPeople()
    {
        return people;
    }

    public String getAddress()
    {
        return address;
    }

    public int getRent()
    {
        return rent;
    }

    public String getPic()
    {
        return pic;
    }

    public int getPrice()
    {
        return price;
    }

    public String getStatus()
    {
        return status;
    }

    public void setNo(String no)
    {
        this.no = no;
    }

    public void setPeople(String people)
    {
        this.people = people;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setRent(int rent)
    {
        this.rent = rent;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
}

class strBuy
{
    String no;
    String people;
    String phone;
    String address;
    int size;
    int rent;
    int price;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getNo()
    {
        return no;
    }

    public String getPeople()
    {
        return people;
    }

    public int getRent()
    {
        return rent;
    }

    public int getPrice()
    {
        return price;
    }

    public void setNo(String no)
    {
        this.no = no;
    }

    public void setPeople(String people)
    {
        this.people = people;
    }

    public void setRent(int rent)
    {
        this.rent = rent;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getAddress()
    {
        return address;
    }

    public int getSize()
    {
        return size;
    }

    public void setAddress(String location)
    {
        this.address = location;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
}

class strDeal
{
    String no;
    String sell;
    String buy;

    public String getSell()
    {
        return sell;
    }

    public String getBuy()
    {
        return buy;
    }

    public void setSell(String sell)
    {
        this.sell = sell;
    }

    public void setBuy(String buy)
    {
        this.buy = buy;
    }

    public String getNo()
    {
        return no;
    }

    public void setNo(String no)
    {
        this.no = no;
    }
}

class strAuth
{
    String no;
    String name;
    String power;

    public String getName()
    {
        return name;
    }

    public String getPower()
    {
        return power;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPower(String power)
    {
        this.power = power;
    }

    public String getNo()
    {
        return no;
    }

    public void setNo(String no)
    {
        this.no = no;
    }
}

class strDealAuto
{
    String sellno;
    String buyno;

    public String getSellno()
    {
        return sellno;
    }

    public String getBuyno()
    {
        return buyno;
    }

    public void setSellno(String sellno)
    {
        this.sellno = sellno;
    }

    public void setBuyno(String buyno)
    {
        this.buyno = buyno;
    }
}