select
  ( @rank := @rank + 1 ) as rank,
  v.name,
  v.votes
from votes v, ( select @rank := 0 ) r_alias
order by v.votes desc;
